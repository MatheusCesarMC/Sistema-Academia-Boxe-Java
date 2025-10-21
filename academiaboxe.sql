-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: academiaboxe
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agendamentos`
--

DROP TABLE IF EXISTS `agendamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agendamentos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_idCliente` int DEFAULT NULL,
  `tipoAula` varchar(100) NOT NULL,
  `Valor` int NOT NULL,
  `horaAgendada` varchar(100) NOT NULL,
  `dataAgendada` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cliente` (`fk_idCliente`),
  CONSTRAINT `fk_cliente` FOREIGN KEY (`fk_idCliente`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agendamentos`
--

LOCK TABLES `agendamentos` WRITE;
/*!40000 ALTER TABLE `agendamentos` DISABLE KEYS */;
INSERT INTO `agendamentos` VALUES (5,18,'Boxe Avançado',70,'13:00','25/06/47');
/*!40000 ALTER TABLE `agendamentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cpf` varchar(10) NOT NULL,
  `endereco` varchar(20) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Sexo` varchar(25) NOT NULL,
  `dataNascimento` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (5,'cesar','02187745','Jardim 22','31997816517','lordteu955@gmail.com','masculino','25/06/25'),(9,'rafa','02187745','2323232','31997816517','lordteu955@gmail.com','masculino','25/06/25'),(18,'Matheus','02187745','Jardim 22yw3w323','31997816517','lordteu955@gmail.com','masculino','25/06/25'),(24,'Matheus','02187745','Jardim 22yw3w323','31997816517','lordteu955@gmail.com','aluno','25/06/25'),(27,'neg','02187745','Jardim 22','31997816517','lordteu955@gmail.com','masculino','25/06/25'),(36,'teste','teste','teste','matheus','teste','teste','teste'),(38,'Teste2','Teste2','Teste2','Teste2','Teste2','Teste2','Teste2'),(39,'mat','mat','mat','mat','mat','mat','mat'),(40,'dadw','dadw','dadw','dadw','dadw','dadw','dadw');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(50) NOT NULL,
  `precoCompra` double NOT NULL,
  `precoVenda` double NOT NULL,
  `qtdeEstoque` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` VALUES (1,'Luva',90,180,25),(2,'Bucal',20,50,10),(3,'Manopla',75,140,5);
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `perfil` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'matheus','123','admin'),(2,'matheus','123','usuario'),(3,'@alunomatheus','123456','administrador');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendas`
--

DROP TABLE IF EXISTS `vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendas` (
  `idvendas` int NOT NULL AUTO_INCREMENT,
  `cliente` varchar(45) NOT NULL,
  `vendedor` varchar(70) NOT NULL,
  `formaDePagamento` varchar(45) NOT NULL,
  `total` varchar(100) NOT NULL,
  `dataVenda` varchar(45) NOT NULL,
  `codigoVenda` varchar(45) NOT NULL,
  `detalhes` text NOT NULL,
  PRIMARY KEY (`idvendas`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendas`
--

LOCK TABLES `vendas` WRITE;
/*!40000 ALTER TABLE `vendas` DISABLE KEYS */;
INSERT INTO `vendas` VALUES (1,'cesar - 02187745','Ippo - 0214556328','Pix','51,00','25/25/25','001','Itens selecionados:<br>Bucal<br>Valor: 51,00 (Desconto: 1,00, Acréscimo: 2,00)<br>'),(2,'cesar - 02187745','Takamura - 65877741269','Pix','140,00','25/25/25','002','Itens selecionados:<br>Manopla<br>Valor: 140,00 (Desconto: 0,00, Acréscimo: 0,00)<br>'),(3,'rafa - 02187745','Ippo - 0214556328','Cartão Crédito','180,00','26/24/27','003','Itens selecionados:<br>Luva\nValor: 180,00 (Desconto: 0,00, Acréscimo: 0,00)\n'),(4,'Matheus - 02187745','Ippo - 0214556328','Cartão Débito','140,00','99/99/99','003','Itens selecionados:<br>Manopla\nValor: 140,00 (Desconto: 0,00, Acréscimo: 0,00)\n'),(5,'Teste2 - Teste2','Takamura - 65877741269','Cartão Débito','50,00','66/66/66','004','Itens selecionados:<br>Bucal\nValor: 50,00 (Desconto: 0,00, Acréscimo: 0,00)\n'),(6,'neg - 02187745','Takamura - 65877741269','Boleto','140,00','44/44/44','005','Itens selecionados:Manopla\nValor: 140,00 (Desconto: 0,00, Acréscimo: 0,00)');
/*!40000 ALTER TABLE `vendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendedores`
--

DROP TABLE IF EXISTS `vendedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendedores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `endereco` varchar(20) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendedores`
--

LOCK TABLES `vendedores` WRITE;
/*!40000 ALTER TABLE `vendedores` DISABLE KEYS */;
INSERT INTO `vendedores` VALUES (1,'Ippo','0214556328','Rua dos Tira 22','31557846598'),(2,'Takamura','65877741269','Rua do Soco 87','31665478513');
/*!40000 ALTER TABLE `vendedores` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-19 13:26:01
