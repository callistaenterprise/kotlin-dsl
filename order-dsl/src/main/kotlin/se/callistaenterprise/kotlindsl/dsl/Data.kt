package se.callistaenterprise.kotlindsl.dsl

import kotlin.random.Random

class Data {

    companion object {

        fun randomId() = "SE0000${Random.nextLong(Long.MAX_VALUE)}"

        fun firstName(): String = firstNames.random()

        fun lastName(): String = lastNames.random()

        private val firstNames = listOf(
            "Anna", "Maria", "Eva", "Karin", "Lena", "Emma", "Kerstin", "Sara", "Malin", "Ingrid", "Linda", "Elin", "Birgitta", "Marie",
            "Inger", "Johanna", "Hanna", "Sofia", "Annika", "Ulla", "Julia", "Susanne", "Jenny", "Carina", "Ida", "Christina", "Helena",
            "Åsa", "Kristina", "Camilla", "Gunilla", "Sandra", "Anita", "Monica", "Amanda", "Cecilia", "Emelie", "Margareta", "Jessica",
            "Frida", "Elsa", "Alice", "Barbro", "Marianne", "Lisa", "Elisabeth", "Siv", "Maja", "Ulrika", "Anette", "Ebba", "Caroline",
            "Katarina", "Agneta", "Lina", "Matilda", "Pia", "Berit", "Gun", "Ella", "Lars", "Anders", "Johan", "Peter", "Jan", "Daniel",
            "Mikael", "Erik", "Per", "Fredrik", "Hans", "Andreas", "Stefan", "Magnus", "Mats", "Jonas", "Bengt", "Alexander", "Martin",
            "Thomas", "Bo", "Karl", "Nils", "Björn", "Leif", "David", "Emil", "Ulf", "Sven", "Simon", "Henrik", "Mattias", "Marcus",
            "Anton", "Patrik", "Robert", "William", "Kjell", "Joakim", "Håkan", "Tommy", "Göran", "Christer", "Adam", "Carl", "Rolf",
            "Lennart", "Robin", "Niklas", "Oscar", "Sebastian", "Elias", "Tobias", "John", "Tomas", "Gustav", "Stig", "Michael", "Filip",
            "Axel",
        )

        private val lastNames = listOf(
            "Andersson", "Johansson", "Karlsson", "Nilsson", "Eriksson", "Larsson", "Olsson", "Persson", "Svensson", "Gustafsson",
            "Pettersson", "Jonsson", "Jansson", "Hansson", "Bengtsson", "Carlsson", "Jönsson", "Lindberg", "Petersson", "Magnusson",
            "Lindström", "Gustavsson", "Olofsson", "Lindgren", "Axelsson", "Bergström", "Lundberg", "Lundgren", "Berg", "Jakobsson",
            "Berglund", "Sandberg", "Fredriksson", "Mattsson", "Sjöberg", "Forsberg", "Ali", "Henriksson", "Lind", "Lindqvist",
            "Engström", "Eklund", "Lundin", "Danielsson", "Håkansson", "Holm", "Bergman", "Gunnarsson", "Samuelsson", "Nyström",
            "Wallin", "Lundqvist", "Björk", "Fransson", "Holmberg", "Johnsson", "Nyberg", "Söderberg", "Arvidsson", "Isaksson",
            "Nordström", "Ahmed", "Mårtensson", "Lundström", "Björklund", "Berggren", "Eliasson", "Sandström", "Nordin", "Ström",
            "Åberg", "Ekström", "Hermansson", "Mohamed", "Holmgren", "Hedlund", "Sundberg", "Sjögren", "Dahlberg", "Öberg", "Hellström",
            "Martinsson", "Blom", "Strömberg", "Ek", "Falk", "Månsson", "Abrahamsson", "Blomqvist", "Lindholm", "Norberg", "Åkesson",
            "Sundström", "Löfgren", "Dahl", "Åström", "Jonasson", "Hassan", "Söderström", "Jensen", "Nyman", "Ivarsson", "Andreasson",
            "Bergqvist", "Ibrahim", "Lund", "Hansen", "Palm", "Göransson", "Borg",
        )

    }
}
