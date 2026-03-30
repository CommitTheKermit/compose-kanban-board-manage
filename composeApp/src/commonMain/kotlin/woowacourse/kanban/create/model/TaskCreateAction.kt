package woowacourse.kanban.create.model

import woowacourse.kanban.commonmodel.Assignee
import woowacourse.kanban.commonmodel.BoardData
import woowacourse.kanban.commonmodel.KanbanTask
import woowacourse.kanban.commonmodel.Tags
import woowacourse.kanban.commonmodel.TaskStatus
import woowacourse.kanban.commonmodel.Title

class TaskCreateAction {

    fun validate(
        title: String,
        tags: String,
    ): ValidationResult {
        val isTitleError = title.isEmpty()
        val tagList = tags.split(",")
        val isTagError = tagList.size > 5 || tagList.any { it.length > 5 }
        return ValidationResult(isTitleError, isTagError)
    }

    fun createTask(
        title: String,
        content: String,
        tags: String,
        statusIndex: Int,
        assignee: Assignee,
    ): KanbanTask {
        return KanbanTask(
            data = BoardData(
                title = Title(title),
                content = content,
                tags = Tags(
                    if (tags.isNotBlank()) tags.split(",") else emptyList(),
                ),
                nickname = assignee.nickname,
            ),
            status = TaskStatus.entries[statusIndex],
        )
    }
}
