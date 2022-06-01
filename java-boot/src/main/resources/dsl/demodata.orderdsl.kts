val ann = customer {
    firstName = "Ann"
    lastName = "Svensson"
}

val cal = customer {
    firstName = "Cal"
    lastName = "Carlsson"
}

val computer = product("Computer")
val keyboard = product("Keyboard")
val mouse = product("Mouse")

repeat(10) {
    order(ann) {
        item(computer, Random.nextLong(50))
        item(keyboard, Random.nextLong(50))
        item(mouse, Random.nextLong(50))
    }
}

order(cal) {
    item(computer)
    item(keyboard)
    item(mouse, 2)
}
