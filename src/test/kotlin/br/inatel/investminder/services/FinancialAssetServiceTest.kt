package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.repositories.FinancialAssetRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
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

    @Test
    fun `should return a empty list of assets`() {
        val assetList = listOf<FinancialAsset>()

        `when`(financialAssetRepository.findAll()).thenReturn(assetList)

        val actual: List<FinancialAsset> = financialAssetService.getAllAssets()

        verify(financialAssetRepository, times(1)).findAll()

        assertEquals(assetList.size, actual.size)
        assertEquals(assetList, actual)
    }

    @Test
    fun `should return a financial asset by id`() {
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

        `when`(financialAssetRepository.findById(anyLong())).thenReturn(Optional.of(assetResponse))

        val actual: Optional<FinancialAsset> = financialAssetService.getAssetById(assetResponse.id!!)

        verify(financialAssetRepository, times(1)).findById(anyLong())

        assertEquals(assetResponse, actual.get())
    }

    @Test
    fun `should return a empty optional`() {
        val assetResponse = Optional.empty<FinancialAsset>()

        `when`(financialAssetRepository.findById(anyLong())).thenReturn(assetResponse)

        val actual: Optional<FinancialAsset> = financialAssetService.getAssetById(Random().nextInt())

        verify(financialAssetRepository, times(1)).findById(anyLong())

        assertEquals(assetResponse, actual)
    }

    @Test
    fun `should return a financial asset by name`() {
        val date = LocalDateTime.now()
        val assetResponse1 = FinancialAsset(
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

        val assetList = listOf(assetResponse1, assetResponse2)

        `when`(financialAssetRepository.findByNameContaining(anyString())).thenReturn(assetList)

        val actual: List<FinancialAsset> = financialAssetService.getAssetByName("Teste")

        verify(financialAssetRepository, times(1)).findByNameContaining(anyString())

        assertEquals(assetList, actual)
    }

    @Test
    fun `should return a empty list of assets by name`() {
        val assetList = listOf<FinancialAsset>()

        `when`(financialAssetRepository.findByNameContaining(anyString())).thenReturn(assetList)

        val actual: List<FinancialAsset> = financialAssetService.getAssetByName("Teste")

        verify(financialAssetRepository, times(1)).findByNameContaining(anyString())

        assertEquals(assetList, actual)
    }

    @Test
    fun `should return a financial asset by type`() {
        val date = LocalDateTime.now()
        val assetResponse1 = FinancialAsset(
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

        val assetList = listOf(assetResponse1, assetResponse2)

        `when`(financialAssetRepository.findByType(anyString())).thenReturn(assetList)

        val actual: List<FinancialAsset> = financialAssetService.getAssetByType("Teste")

        verify(financialAssetRepository, times(1)).findByType(anyString())

        assertEquals(assetList, actual)
    }

    @Test
    fun `should return a empty list of assets by type`() {
        val assetList = listOf<FinancialAsset>()

        `when`(financialAssetRepository.findByType(anyString())).thenReturn(assetList)

        val actual: List<FinancialAsset> = financialAssetService.getAssetByType("Teste")

        verify(financialAssetRepository, times(1)).findByType(anyString())

        assertEquals(assetList, actual)
    }

    @Test
    fun `should update a financial asset by id`() {
        val date = LocalDateTime.now().minusDays(1)
        val assetRequest = FinancialAssetRequestDTO(
                name = "Teste",
                type = "Teste",
                price = 10.0,
                company = "Teste"
        )
        val expected = FinancialAsset(
                id = Random().nextInt(),
                name = "Teste",
                type = "Teste",
                price = 10.0,
                company = "Teste",
                createdAt = date,
                updateAt = date
        )

        `when`(financialAssetRepository.findById(anyLong())).thenReturn(Optional.of(expected))
        `when`(financialAssetRepository.save(any(FinancialAsset::class.java))).thenReturn(expected)

        val actual: FinancialAsset = financialAssetService.updateAssetById(expected.id!!, assetRequest)

        verify(financialAssetRepository, times(1)).findById(anyLong())
        verify(financialAssetRepository, times(1)).save(any(FinancialAsset::class.java))

        assertEquals(expected, actual)
        assertNotEquals(expected.createdAt, actual.updateAt)
    }

}
