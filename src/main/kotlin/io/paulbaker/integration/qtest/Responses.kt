package io.paulbaker.integration.qtest

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.URL
import java.time.LocalDateTime

// Reference this for @JsonFormat patterns https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
const val RECEIVING_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXXXX"

/**
 * @see <a href="https://api.qasymphony.com/#/login/postAccessToken">qTest API</a>
 */
data class LoginTokenAuthenticator(
        val accessToken: String?,
        val tokenType: String?,
        val refreshToken: String?,
        val scope: Set<String>,
        var agent: String?) : Authenticator {

    override fun authenticate(route: Route?, response: Response?): Request? {
        return response?.request()?.newBuilder()
                ?.header("Authorization", "$tokenType $accessToken")
                ?.build()
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
        val id: Int,
        val username: String,
        val email: String,
        @JsonProperty("first_name")
        val firstName: String,
        @JsonProperty("last_name")
        val lastName: String,
        val status: Int,
        val avatar: String,
        @JsonProperty("ldap_username")
        val ldapUsername: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Project(
        val id: Int,
        val name: String,
        val description: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("start_date")
        val startDate: LocalDateTime?,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("end_date")
        val endDate: LocalDateTime?,
        @JsonProperty("automation")
        val automationEnabled: Boolean
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Release(
        val id: Int,
        val name: String,
        val description: String,
        val pid: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("created_date")
        val createdDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("last_modified_date")
        val lastModifiedDate: LocalDateTime,
        @JsonProperty("web_url")
        val url: URL
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TestCycle(
        val id: Int,
        val name: String,
//        val description: String,
        val pid: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("created_date")
        val createdDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("last_modified_date")
        val lastModifiedDate: LocalDateTime,
        @JsonProperty("web_url")
        val url: URL
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TestRun(
        val id: Int,
        @JsonProperty("parent_id")
        val parentId: Int,
        val name: String,
        val pid: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("created_date")
        val createdDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("last_modified_date")
        val lastModifiedDate: LocalDateTime
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Module(
        val id: Int,
        val name: String,
        val pid: String,
        @JsonProperty("parent_id")
        val parentId: Int,
        val children: List<Module>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Testcase(
        val id: Int,
        @JsonProperty("parent_id")
        val parentId: Int,
        val name: String,
        val description: String,
        val precondition: String,
        @JsonProperty("test_case_version_id")
        val version: Double,
        val pid: String,
        @JsonProperty("creator_id")
        val creatorId: Int,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("created_date")
        val createdDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("last_modified_date")
        val lastModifiedDate: LocalDateTime,
        @JsonProperty("web_url")
        val url: URL,
        @JsonProperty("properties")
        val properties: List<TestcaseProperties>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TestcaseProperties(
        @JsonProperty("field_id")
        val id: Int,
        @JsonProperty("field_name")
        val name: String,
        @JsonProperty("field_value")
        val value: String,
        @JsonProperty("field_value_name")
        val valueName: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Requirement(
        val id: Int,
        @JsonProperty("parent_id")
        val parentId: Int,
        val name: String,
        val pid: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("created_date")
        val createdDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RECEIVING_DATE_PATTERN)
        @JsonProperty("last_modified_date")
        val lastModifiedDate: LocalDateTime
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Field(
        val id: Int,
        @JsonProperty("label")
        val name: String,
        val required: Boolean,
        @JsonProperty("allowed_values")
        val options: List<FieldOption>?,
        @JsonProperty("is_active")
        val active: Boolean,
        @JsonProperty("searchable")
        val searchable: Boolean
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class FieldOption(
        @JsonProperty("value")
        val id: Int,
        val label: String,
        @JsonProperty("is_active")
        val active: Boolean,
        @JsonProperty("is_default")
        val default: Boolean,
        val color: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PaginatedResponse<T>(
        val page: Int,
        @JsonProperty("page_size")
        val pageSize: Int,
        val total: Int,
        val items: List<T>
)