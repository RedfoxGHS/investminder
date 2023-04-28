package br.inatel.investminder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InvestminderApplication

fun main(args: Array<String>) {
	runApplication<InvestminderApplication>(*args)
}
