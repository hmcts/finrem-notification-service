locals {
  ase_name = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"
  local_env = "${(var.env == "preview" || var.env == "spreview") ? (var.env == "preview" ) ? "aat" : "saat" : var.env}"

  previewVaultName = "${var.reform_team}-aat"
  nonPreviewVaultName = "${var.reform_team}-${var.env}"
  vaultName = "${var.env == "preview" ? local.previewVaultName : local.nonPreviewVaultName}"
  vaultUri = "${data.azurerm_key_vault.finrem_key_vault.vault_uri}"

  asp_name = "${var.env == "prod" ? "finrem-ns-prod" : "${var.raw_product}-${var.env}"}"
  asp_rg = "${var.env == "prod" ? "finrem-ns-prod" : "${var.raw_product}-${var.env}"}"
}

module "finrem-ns" {
  source                          = "git@github.com:hmcts/cnp-module-webapp?ref=master"
  product                         = "${var.product}-${var.component}"
  location                        = "${var.location}"
  env                             = "${var.env}"
  ilbIp                           = "${var.ilbIp}"
  subscription                    = "${var.subscription}"
  appinsights_instrumentation_key = "${var.appinsights_instrumentation_key}"
  capacity                        = "${var.capacity}"
  is_frontend                     = false
  common_tags                     = "${var.common_tags}"
  asp_name                        = "${local.asp_name}"
  asp_rg                          = "${local.asp_rg}"

  app_settings = {
    REFORM_SERVICE_NAME                                   = "${var.reform_service_name}"
    REFORM_TEAM                                           = "${var.reform_team}"
    REFORM_ENVIRONMENT                                    = "${var.env}"
    UK_GOV_NOTIFY_API_KEY                                 = "${data.azurerm_key_vault_secret.gov-uk-notification-key.value}"
    UK_GOV_NOTIFY_EMAIL_TEMPLATES                         = "${data.azurerm_key_vault_secret.gov-uk-notification-email-templates.value}"
    SWAGGER_ENABLED                                       = "${var.swagger_enabled}"
  }
}

data "azurerm_key_vault" "finrem_key_vault" {
  name                = "${local.vaultName}"
  resource_group_name = "${local.vaultName}"
}

data "azurerm_key_vault_secret" "gov-uk-notification-key" {
  name      = "gov-uk-notification-key"
  vault_uri = "${data.azurerm_key_vault.finrem_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "gov-uk-notification-email-templates" {
  name      = "gov-uk-notification-email-templates"
  vault_uri = "${data.azurerm_key_vault.finrem_key_vault.vault_uri}"
}