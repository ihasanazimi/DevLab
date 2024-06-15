package ir.ha.meproject.model.data.developer_info

data class DeveloperInfo(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val profileImage: String,
    val birthDate: String,
    val jobTitle: String,
    val contactInfo: ContactInfo,
    val skills: List<String>,
    val resume: Resume
)