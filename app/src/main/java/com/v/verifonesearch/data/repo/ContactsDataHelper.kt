package com.v.verifonesearch.data.repo

object ContactsDataHelper {
    private val contactNames = listOf(
        "Alice Anderson", "Alex Adams", "Anna Armstrong", "Andrew Allen", "Amy Abbott",
        "Bob Brown", "Brian Baker", "Betty Barnes", "Bruce Bennett", "Bella Brooks",
        "Charlie Chen", "Christina Clark", "Chris Cooper", "Catherine Cole", "Carlos Cruz",
        "David Davis", "Diana Duncan", "Daniel Drake", "Deborah Dean", "Derek Douglas",
        "Emma Evans", "Eric Edwards", "Elizabeth Ellis", "Edward Elliott", "Emily Foster",
        "Frank Fisher", "Fiona Fleming", "Fred Freeman", "Faith Ferguson", "Felix Flynn",
        "Grace Green", "George Garcia", "Gloria Gibson", "Gary Graham", "Gina Gray",
        "Henry Harris", "Hannah Hughes", "Helen Hunt", "Howard Hill", "Holly Hayes",
        "Isaac Johnson", "Isabella Jackson", "Ian Irving", "Iris Ingram", "Ivan Ivers",
        "James Johnson", "Jennifer Jones", "John Jackson", "Jessica Jenkins", "Jack Jordan",
        "Karen King", "Kevin Kelly", "Katherine Knight", "Kyle Kim", "Kimberly Kane",
        "Linda Lewis", "Luke Lee", "Laura Lopez", "Larry Long", "Lisa Lane",
        "Michael Miller", "Maria Martinez", "Mark Moore", "Michelle Morgan", "Matthew Murphy",
        "Nancy Nelson", "Nicholas Newman", "Nicole Nash", "Nathan North", "Natalie Noble",
        "Oliver O'Connor", "Olivia Owen", "Oscar Olson", "Opal Ortiz", "Owen O'Brien",
        "Peter Parker", "Patricia Phillips", "Paul Peterson", "Pamela Powell", "Patrick Price",
        "Quinn Quincy", "Quincy Queen", "Quentin Quick", "Queenie Quest", "Quiana Quill",
        "Robert Rodriguez", "Rachel Roberts", "Richard Robinson", "Rebecca Reed", "Ryan Ross",
        "Sarah Smith", "Steven Scott", "Susan Stewart", "Samuel Sanders", "Stephanie Stone",
        "Thomas Taylor", "Teresa Turner", "Timothy Thompson", "Tiffany Tucker", "Tony Torres",
        "Ursula Underwood", "Ulysses Urban", "Uma Upton", "Uri Unger", "Unity Urich",
        "Victoria Valdez", "Vincent Vaughn", "Vanessa Villa", "Victor Vega", "Violet Vance",
        "William Wilson", "Wendy Walker", "Walter White", "Whitney Webb", "Wayne Ward",
        "Xavier Xu", "Ximena Xiong", "Xander Xie", "Xara Xavier", "Xerxes Xing",
        "Yvonne Young", "Yuki Yamamoto", "Yasmin York", "Yolanda Yang", "Yves Yates",
        "Zachary Zhang", "Zoe Zimmerman", "Zack Zeller", "Zara Zane", "Zion Zhou"
    )

    fun getRandom5Contacts(): List<String> {
        return contactNames.shuffled().take(5)
    }

    fun getAllContacts(): List<String> {
        return contactNames
    }
}