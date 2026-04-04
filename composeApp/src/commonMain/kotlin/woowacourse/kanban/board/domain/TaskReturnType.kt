package woowacourse.kanban.board.domain

sealed class StatusChangeResult {
    data class Success(val project: KanbanProject) : StatusChangeResult()
    data object NotAssigned : StatusChangeResult()
    data object NotChangeable : StatusChangeResult()
}

sealed class TaskChangeResult {
    data class Success(val task: KanbanTask) : TaskChangeResult()
    data object NotAssigned : TaskChangeResult()
    data object NotChangeable : TaskChangeResult()
}

sealed class DeleteResult {
    data class Success(val project: KanbanProject) : DeleteResult()
    data object NotDeletable : DeleteResult()
}

enum class DeleteReturnType {
    DELETE_SUCCESS,
    NOT_DELETABLE,
    NOT_FOUND,
}

enum class ChangeStatusReturnType {
    CHANGE_SUCCESS,
    NOT_CHANGEABLE,
    NOT_ASSIGNED,
    NOT_FOUND,
}
