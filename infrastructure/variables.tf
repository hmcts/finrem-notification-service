variable "product" {
  type = "string"
  default = "fr"
}

variable "app" {
  type = "string"
  default = "dss"
}

variable "location" {
  type    = "string"
  default = "UK South"
}

variable "env" {
  type = "string"
}

variable "infrastructure_env" {
  default     = "dev"
  description = "Infrastructure environment to point to"
}

variable "subscription" {
  type = "string"
}

variable "tenant_id" {
  description = "(Required) The Azure Active Directory tenant ID that should be used for authenticating requests to the key vault. This is usually sourced from environemnt variables and not normally required to be specified."
}

variable "jenkins_AAD_objectId" {
  type = "string"
  description = "(Required) The Azure AD object ID of a user, service principal or security group in the Azure Active Directory tenant for the vault. The object ID must be unique for the list of access policies."
}

variable "vault_section" {
  default = "test"
}

variable "ilbIp"{}

variable "capacity" {
  default = "1"
}


//if possible move this to application.property
variable "auth_provider_service_client_microservice" {
  default = "finrem_notification_store"
}

//if possible move this to application.property
variable "auth_provider_service_client_tokentimetoliveinseconds" {
  default = "900"
}

variable "uk_gov_notify_email_templates" {
  type = "string"
}

variable "uk_gov_notify_email_template_vars" {
  type = "string"
}

variable "idam_api_baseurl" {
  type = "string"
}