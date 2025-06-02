package ir.hasanazimi.me.data.entities.developer_info

data class DeveloperInfo(
    val id: Int = 0,
    val firstName: String ="",
    val lastName: String = "",
    val profileImage: String = "",
    val birthDate: String = "",
    val jobTitle: String ="",
    val contactInfo: ContactInfo = ContactInfo(),
    val skills: List<String> = arrayListOf(),
    val resume: Resume = Resume()
)