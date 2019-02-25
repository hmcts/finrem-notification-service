output "env" {
  value = "${var.env}"
}

output "test_environment" {
  value = "${local.local_env}"
}

output "vaultUri" {
  value = "${data.azurerm_key_vault.finrem_key_vault.vault_uri}"
}

output "gov_uk_notify_key" {
  value = "${data.azurerm_key_vault_secret.gov-uk-notification-key.value}"
}

output "gov-uk-notification-email-templates" {
  value = "${data.azurerm_key_vault_secret.gov-uk-notification-email-templates.value}"
}