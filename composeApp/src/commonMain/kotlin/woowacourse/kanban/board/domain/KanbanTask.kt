package woowacourse.kanban.board.domain

import woowacourse.kanban.domain.Assignee
import woowacourse.kanban.domain.BoardData
import woowacourse.kanban.domain.Tags
import woowacourse.kanban.domain.TaskStatus
import woowacourse.kanban.domain.Title

class KanbanTask(val data: BoardData, val status: TaskStatus) {

    constructor(
        title: Title,
        content: String,
        tags: Tags,
        assignee: Assignee?,
        status: TaskStatus,
        id: Long? = System.currentTimeMillis(),
    ) : this(
        data = BoardData(
            title = title,
            content = content,
            tags = if (tags.tags.all { it.isNotBlank() }) tags else Tags(emptyList()),
            assignee = assignee,
            id = id
                ?: System.currentTimeMillis(),
        ),
        status = status,
    )

    fun copy(
        inputData: BoardData = data,
        inputStatus: TaskStatus = status,
    ): KanbanTask {
        return KanbanTask(inputData, inputStatus)
    }

    fun changeStatus(targetStatus: TaskStatus): TaskChangeResult {
        if (status == TaskStatus.TO_DO && data.assignee == null) {
            return TaskChangeResult.NotAssigned
        }
        if (status.isChangeable(targetStatus)) {
            return TaskChangeResult.Success(copy(data, targetStatus))
        }
        return TaskChangeResult.NotChangeable
    }
}
