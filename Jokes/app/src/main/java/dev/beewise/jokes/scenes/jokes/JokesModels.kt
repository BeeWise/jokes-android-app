package dev.beewise.jokes.scenes.jokes

import android.text.SpannableString
import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.operations.base.errors.OperationError
import java.util.UUID

class JokesModels {
    class PaginationModel {
        var isFetchingItems: Boolean = false
        var noMoreItems: Boolean = false
        var hasError: Boolean = false
        var currentPage: Int = 0
        var limit: Int = 10
        var items: ArrayList<Joke> = arrayListOf()

        var readJokes: ArrayList<Joke> = arrayListOf()

        fun reset() {
            this.isFetchingItems = false
            this.noMoreItems = false
            this.hasError = false
            this.currentPage = 0
            this.limit = 10
            this.items = arrayListOf()

            this.readJokes = arrayListOf()
        }
    }

    enum class ItemType {
        jokeText,
        jokeQna,
        space;
    }

    class DisplayedItem(val uuid: String = UUID.randomUUID().toString(), var type: ItemType, var model: Any?)

    class ItemsPresentation {
        class Response(val items: ArrayList<Joke>, val readJokes: ArrayList<Joke>)

        class ViewModel(val items: ArrayList<DisplayedItem>)
    }

    class NoMoreItemsPresentation {
        class ViewModel(val text: SpannableString)
    }

    class ErrorPresentation {
        class Response(val error: OperationError)

        class ViewModel(val text: SpannableString)
    }

    class ItemSelection {
        class Request(val id: String?)
    }

    class ItemReadState {
        class Response(val isRead: Boolean, val id: String?)

        class ViewModel(val isRead: Boolean, val id: String?)
    }

    class ItemScroll {
        class Response(val animated: Boolean, val index: Int)

        class ViewModel(val animated: Boolean, val index: Int)
    }
}
