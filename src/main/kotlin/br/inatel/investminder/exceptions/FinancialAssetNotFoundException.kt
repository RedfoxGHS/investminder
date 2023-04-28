package br.inatel.investminder.exceptions

class FinancialAssetNotFoundException : RuntimeException() {
    override val message: String
        get() = "Financial asset not found"
}