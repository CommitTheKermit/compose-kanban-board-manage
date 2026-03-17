package woowacourse.kanban.create.model

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.model.Assignee
import woowacourse.kanban.model.BoardData
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.Tags
import woowacourse.kanban.model.TaskStatus
import woowacourse.kanban.model.Title

class TaskCreateState {
    var titleInputValue by mutableStateOf("")
        private set
    var contentInputValue by mutableStateOf("")
        private set
    var tagInputValue by mutableStateOf("")
        private set

    var isTitleError by mutableStateOf(false)
        private set
    var isTagError by mutableStateOf(false)
        private set
    val isCreateError by derivedStateOf { isTitleError || isTagError }

    var selectedStatusIndex by mutableIntStateOf(0)
        private set
    var selectedAssigneeIndex by mutableIntStateOf(0)
        private set

    fun onTitleChange(input: String) {
        titleInputValue = input
        if (isTitleError) isTitleError = false
    }

    fun onContentChange(input: String) {
        contentInputValue = input
    }

    fun onTagChange(input: String) {
        tagInputValue = input
        if (isTagError) isTagError = false
    }

    fun onStatusSelect(index: Int) {
        selectedStatusIndex = index
    }

    fun onCoachSelect(index: Int) {
        selectedAssigneeIndex = index
    }

    fun onCreateValidate(): Boolean {
        isTitleError = titleInputValue.isEmpty()
        val tags = tagInputValue.split(",")
        isTagError = tags.size > 5 || tags.any { it.length > 5 }

        if (isTitleError) titleInputValue = ""
        if (isTagError) tagInputValue = ""

        return isTitleError || isTagError
    }

    fun taskCreate(assignee: Assignee): KanbanTask {
        val task = KanbanTask(
            data = BoardData(
                title = Title(titleInputValue),
                content = contentInputValue,
                tags = Tags(
                    if (tagInputValue.isNotBlank())
                        tagInputValue.split(",")
                    else
                        emptyList(),
                ),
                nickname = assignee.nickname,
            ),
            status = TaskStatus.entries[(selectedStatusIndex)],
        )

        return task
    }
}
