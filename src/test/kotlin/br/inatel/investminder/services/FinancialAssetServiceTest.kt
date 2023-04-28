package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.repositories.FinancialAssetRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class FinancialAssetServiceTest() {

    private lateinit var financialAssetService: FinancialAssetService

    @Mock
    private lateinit var financialAssetRepository: FinancialAssetRepository

    @BeforeEach
    fun setUp() {
        financialAssetService = FinancialAssetService(financialAssetRepository)
    }

    @Test
    fun `should create a new asset`() {
        val assetRequest = FinancialAssetRequestDTO(
            name = "Teste",
            type = "Teste",
            price = 10.0,
            company = "Teste"
        )

        val date = LocalDateTime.now()
        val assetResponse = FinancialAsset(
            id = Random().nextInt(),
            name = "Teste",
            type = "Teste",
            price = 10.0,
            company = "Teste",
            createdAt = date,
            updateAt = date
        )

        `when`(financialAssetRepository.save(any(FinancialAsset::class.java))).thenReturn(assetResponse)

        val actual = financialAssetService.createAsset(assetRequest)

        verify(financialAssetRepository, times(1)).save(any(FinancialAsset::class.java))

        assertEquals(assetResponse, actual)
    }

    @Test
    fun `should return a list of assets`() {
        val date = LocalDateTime.now()
        val assetResponse = FinancialAsset(
                id = Random().nextInt(),
                name = "Teste",
                type = "Teste",
                price = 10.0,
                company = "Teste",
                createdAt = date,
                updateAt = date
        )
        val assetResponse2 = FinancialAsset(
                id = Random().nextInt(),
                name = "Teste2",
                type = "Teste2",
                price = 10.0,
                company = "Teste2",
                createdAt = date,
                updateAt = date
        )
        val assetResponse3 = FinancialAsset(
                id = Random().nextInt(),
                name = "Teste3",
                type = "Teste3",
                price = 10.0,
                company = "Teste3",
                createdAt = date,
                updateAt = date
        )

        val assetList = listOf(assetResponse, assetResponse2, assetResponse3)

        `when`(financialAssetRepository.findAll()).thenReturn(assetList)

        val actual: List<FinancialAsset> = financialAssetService.getAllAssets()

        verify(financialAssetRepository, times(1)).findAll()

        assertEquals(assetList.size, actual.size)
        assertEquals(assetList, actual)
    }


}
