import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myshoppinglist.repositories.ListItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ShareListTypeState(
    var username: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class ShareListViewModel : ViewModel() {

    var state = mutableStateOf(ShareListTypeState())
        private set

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(username = newValue)
    }

    fun shareListWithUid(docId: String, uid: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        ListItemRepository.addOwnerToList(
            docId = docId,
            uid = uid,
            onSuccess = {
                onSuccess()
            },
            onFailure = { exception ->
                onFailure(exception.message ?: "Erro ao compartilhar a lista.")
            }
        )
    }
}

