package uk.fernando.measure.navigation

import androidx.navigation.NamedNavArgument

interface NavigationCommand {
    val name: String
    val arguments: List<NamedNavArgument>
}

object Directions {

    val splash = object : NavigationCommand {
        override val name: String
            get() = "splash"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    val length = object : NavigationCommand {
        override val name: String
            get() = "length"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    val weight = object : NavigationCommand {
        override val name: String
            get() = "weight"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    val temperature = object : NavigationCommand {
        override val name: String
            get() = "temperature"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    val volume = object : NavigationCommand {
        override val name: String
            get() = "volume"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    val addUnit = object : NavigationCommand {
        override val name: String
            get() = "add_unit"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    val settings = object : NavigationCommand {
        override val name: String
            get() = "settings"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
}


