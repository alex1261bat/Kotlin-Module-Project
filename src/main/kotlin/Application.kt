import java.util.Scanner

class Application {
    private var exit = false
    private val menu = Menu()
    private val scanner = Scanner(System.`in`)
    private val archives = mutableListOf<Archive>()

    fun start() {
        while (!exit) {
            printMenu(menu.mainMenu)

            when (validateUserInput(menu.mainMenu)) {
                1 -> createArchive()
                2 -> openArchive()
                3 -> exit = true
            }

        }
        println()
    }

    private fun openArchive() {
        while (true) {
            printMenu(menu.archiveMenu)

            when (validateUserInput(menu.archiveMenu)) {
                1 -> getArchiveFromList()
                2 -> getArchiveByTitle()
                3 -> return
            }
        }
    }

    private fun operateNote(archive: Archive) {
        while (true) {
            printMenu(menu.noteMenu)

            when (validateUserInput(menu.noteMenu)) {
                1 -> createNote(archive)
                2 -> getNoteFromList(archive)
                3 -> getNoteByTitle(archive)
                4 -> return
            }
        }
    }

    private fun getArchiveFromList() {
        val menuList = mutableListOf<String>()

        if (archives.isNotEmpty()) {
            println("Введите номер архива из списка")
            archives.forEachIndexed { index, archive ->
                menuList.add("${index + 1}. ${archive.title}") }

        } else {
            println("Еще нет созданных архивов")
            return
        }

        printMenu(menuList)
        val index = validateUserInput(menuList)
        val archive = archives[index - 1]
        println("Выбран архив ${archive.title}")
        operateNote(archive)
    }

    private fun getArchiveByTitle() {
        var archive = Archive("example")

        if (archives.isNotEmpty()) {
            println("Введите название архива")
            scanner.nextLine()
            val title: String = scanner.nextLine()

            for (element in archives) {
                if (element.title.lowercase() == title.lowercase()) {
                    archive = element
                    println("Выбран архив ${archive.title}")
                } else {
                    println("Архив с названием $title не существует")
                    return
                }
            }
        } else {
            println("Еще нет созданных архивов")
            return
        }
        operateNote(archive)
    }

    private fun getNoteFromList(archive: Archive) {
        val menuList = mutableListOf<String>()
        if (archive.notes.isNotEmpty()) {
            println("Введите номер заметки из списка")
            archive.notes.forEachIndexed { index, element ->
                menuList.add("${index + 1}. ${element.title}") }
        } else {
            println("Еще нет созданных заметок")
            return
        }

        printMenu(menuList)
        val index = validateUserInput(menuList)
        val note = archive.notes[index - 1]
        printNote(note)
    }

    private fun getNoteByTitle(archive: Archive) {
        var note: Note

        if (archive.notes.isNotEmpty()) {
            println("Введите название заметки")
            scanner.nextLine()
            val title: String = scanner.nextLine()

            for (element in archive.notes) {
                if (element.title.lowercase() == title.lowercase()) {
                    note = element
                    printNote(note)
                } else {
                    println("Заметка с названием $title не существует")
                    return
                }
            }
        } else {
            println("Еще нет созданных заметок")
            return
        }
    }

    private fun createNote(archive: Archive) {
        println("Введите название заметки")
        scanner.nextLine()
        val title = scanner.nextLine()
        println("Введите описание заметки")
        val description = scanner.nextLine()
        val note = Note(title, description)

        if (!archive.notes.contains(note)) {
            archive.notes.add(note)
            println("Создана заметка: $title")
        } else {
            println("Заметка с названием: $title уже существует")
        }
    }

    private fun createArchive() {
        println("Введите название архива")
        scanner.nextLine()
        val title = scanner.nextLine()
        val archive = Archive(title, mutableListOf())

        if (!archives.contains(archive)) {
            archives.add(archive)
            println("Создан архив: $title")
        } else {
            println("Архив с названием: $title уже существует")
        }
    }

    private fun validateUserInput(menu: List<String>): Int {
        val output: Int

        while (true) {
            if (scanner.hasNextInt()) {
                val input = scanner.nextInt()
                if (input in 1..menu.size) {
                    output = input
                    break
                } else {
                    println("Введите номер пункта из меню")
                    printMenu(menu)
                }
            } else {
                println("Введен неверный символ \nВведите номер пункта из меню")
                printMenu(menu)
                scanner.nextLine()
            }
        }
        return output
    }

    private fun printMenu(menu: List<String>) {
        println()
        println("Введите номер выбранного пункта")

        for (element in menu) {
            println("   $element")
        }
    }

    private fun printNote(note: Note) {
        println("Выбрана заметка:\n" +
                "   название: ${note.title}\n" +
                "   содержание: ${note.description}")
    }
}
