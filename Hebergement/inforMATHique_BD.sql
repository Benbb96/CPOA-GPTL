-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mar 01 Décembre 2015 à 14:01
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `informathique`
--

-- --------------------------------------------------------

--
-- Structure de la table `chapitre`
--

CREATE TABLE IF NOT EXISTS `chapitre` (
  `idChapitre` int(11) NOT NULL AUTO_INCREMENT,
  `idModule` int(11) NOT NULL,
  `nomChapitre` varchar(100) NOT NULL,
  PRIMARY KEY (`idChapitre`),
  KEY `idModule` (`idModule`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Contenu de la table `chapitre`
--

INSERT INTO `chapitre` (`idChapitre`, `idModule`, `nomChapitre`) VALUES
(1, 1, 'Notion d''ensembles'),
(2, 1, 'Numération'),
(3, 1, 'Algèbre de Boole'),
(4, 1, 'Calcul propositionnel'),
(5, 1, 'Prédicats'),
(6, 1, 'Ensembles'),
(7, 1, 'Somme, suites, récurrence'),
(8, 1, 'Demonstration'),
(9, 1, 'Fonctions'),
(10, 2, 'Syst'),
(11, 2, 'Ensembles Vectoriels'),
(12, 2, 'FamilleEtBases'),
(13, 2, 'ApplLin'),
(14, 2, 'Matrices'),
(15, 2, 'Matrices et applications linéaires');

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE IF NOT EXISTS `compte` (
  `idCompte` int(11) NOT NULL AUTO_INCREMENT,
  `nomCompte` varchar(25) NOT NULL,
  `prenomCompte` varchar(25) NOT NULL,
  `mailCompte` varchar(100) NOT NULL,
  `mdpCompte` varchar(100) NOT NULL,
  `dateCreation` date NOT NULL,
  `cleConfirmation` varchar(100) DEFAULT NULL,
  `dateActivation` date DEFAULT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `idRang` int(11) NOT NULL DEFAULT '1',
  `idNiveau` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idCompte`),
  KEY `idRang` (`idRang`),
  KEY `idNiveau` (`idNiveau`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `compte`
--

INSERT INTO `compte` (`idCompte`, `nomCompte`, `prenomCompte`, `mailCompte`, `mdpCompte`, `dateCreation`, `cleConfirmation`, `dateActivation`, `points`, `idRang`, `idNiveau`) VALUES
(1, 'nivel', 'yoann', 'yoann.nivel@etu.univ-lyon1.fr', '$2y$10$2KqX2ISUld7VFeMP5yaxfOMEGzIg89cVCoY3WbUIwnnx4KBnCGy22', '2015-11-26', NULL, '2015-11-26', 0, 1, 1),
(2, 'reziga', 'fiona', 'fiona.reziga@etu.univ-lyon1.fr', '$2y$10$I//30V.9GmSdQHKThnSip.PbIzw.dTdSr6sxVCUH0mWq/R1/hyqn.', '2015-11-26', NULL, '2015-11-26', 0, 1, 1),
(3, 'mounier', 'lisa', 'lisa.mounier@etu.univ-lyon1.fr', '$2y$10$bF1oL9mFYm.mXllI3wSWxevB59ZUqC4cckwwI/MPKHaKVCirADSae', '2015-11-26', NULL, '2015-11-26', 0, 1, 1),
(4, 'florentin', 'calvin', 'calvin.florentin@etu.univ-lyon1.fr', '$2y$10$lGV4cCww.DHkusmm4zqd7.VIy3rKoN66i/z9/ZR92FlBlX6mRiZ1W', '2015-11-26', NULL, '2015-11-26', 0, 1, 1),
(5, 'masson', 'tom', 'tom.masson@etu.univ-lyon1.fr', '$2y$10$6Mrj8QO9w5F0Y4/KeupxQeMAXtsc6tNldA1hVAOGgLePA1Vdv3YiW', '2015-11-26', NULL, '2015-11-26', 0, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `cours`
--

CREATE TABLE IF NOT EXISTS `cours` (
  `idCours` int(11) NOT NULL AUTO_INCREMENT,
  `idChapitre` int(11) NOT NULL,
  `contenuCours` varchar(100) NOT NULL,
  PRIMARY KEY (`idCours`),
  KEY `idChapitre` (`idChapitre`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `cours`
--

INSERT INTO `cours` (`idCours`, `idChapitre`, `contenuCours`) VALUES
(1, 1, 'Web/RESSOURCES/S1_M1_CH1.pdf'),
(2, 2, 'Web/RESSOURCES/S1_M1_CH2.pdf');

-- --------------------------------------------------------

--
-- Structure de la table `exercice`
--

CREATE TABLE IF NOT EXISTS `exercice` (
  `idExo` int(11) NOT NULL AUTO_INCREMENT,
  `idChapitre` int(11) NOT NULL,
  `enonceExo` varchar(100) NOT NULL,
  PRIMARY KEY (`idExo`),
  KEY `idChapitre` (`idChapitre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `ex_quest_indic_sol`
--

CREATE TABLE IF NOT EXISTS `ex_quest_indic_sol` (
  `idExercice` int(11) NOT NULL,
  `idQuestion` int(11) NOT NULL,
  `idIndice` int(11) NOT NULL,
  `idSolution` int(11) NOT NULL,
  KEY `idExercice` (`idExercice`),
  KEY `idQuestion` (`idQuestion`),
  KEY `idIndice` (`idIndice`),
  KEY `idSolution` (`idSolution`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `indice`
--

CREATE TABLE IF NOT EXISTS `indice` (
  `idIndice` int(11) NOT NULL AUTO_INCREMENT,
  `contenuIndice` varchar(100) NOT NULL,
  PRIMARY KEY (`idIndice`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `module`
--

CREATE TABLE IF NOT EXISTS `module` (
  `idModule` int(11) NOT NULL AUTO_INCREMENT,
  `idSemestre` int(11) NOT NULL,
  `nomModule` varchar(100) NOT NULL,
  PRIMARY KEY (`idModule`),
  KEY `idSemestre` (`idSemestre`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `module`
--

INSERT INTO `module` (`idModule`, `idSemestre`, `nomModule`) VALUES
(1, 1, 'Mathématiques Discrètes'),
(2, 1, 'Algèbre Linéaire'),
(3, 2, 'Graphes et Langages'),
(4, 2, 'Analyse et méthodes numériques'),
(5, 3, 'Probabilités et statistiques'),
(6, 3, 'Modélisations mathématiques'),
(7, 4, 'Introduction à la recherche opérationnelle et aide à la décision');

-- --------------------------------------------------------

--
-- Structure de la table `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `idNews` int(11) NOT NULL AUTO_INCREMENT,
  `idCompte` int(11) NOT NULL,
  `typeNews` int(11) NOT NULL,
  `titreNews` varchar(40) NOT NULL,
  `contenuNews` varchar(1000) NOT NULL,
  `dateNews` date NOT NULL,
  PRIMARY KEY (`idNews`),
  KEY `idCompte` (`idCompte`),
  KEY `typeNews` (`typeNews`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `news`
--

INSERT INTO `news` (`idNews`, `idCompte`, `typeNews`, `titreNews`, `contenuNews`, `dateNews`) VALUES
(1, 1, 1, 'DS : Modelisation', 'Ceci est le contenu. <b>Celui-ci est en gras</b>', '2015-11-26'),
(2, 2, 2, 'Autre news', 'Autre est le contenu', '2015-11-17');

-- --------------------------------------------------------

--
-- Structure de la table `niveau`
--

CREATE TABLE IF NOT EXISTS `niveau` (
  `idNiveau` int(11) NOT NULL AUTO_INCREMENT,
  `nomNiveau` varchar(40) NOT NULL,
  `pointMin` int(11) NOT NULL,
  PRIMARY KEY (`idNiveau`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Contenu de la table `niveau`
--

INSERT INTO `niveau` (`idNiveau`, `nomNiveau`, `pointMin`) VALUES
(1, 'Novice', 0),
(2, 'Débutant', 25),
(3, 'Avancé', 50),
(4, 'Sûr de soi', 100),
(5, 'Confirmé', 200),
(6, 'Expert', 400),
(7, 'Vétéran', 800),
(8, 'Super-Calculateur', 1600),
(9, 'Albert Einstein', 3200),
(10, 'Mr Jaloux', 6400);

-- --------------------------------------------------------

--
-- Structure de la table `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `idQuestion` int(11) NOT NULL AUTO_INCREMENT,
  `contenuQuestion` varchar(100) NOT NULL,
  PRIMARY KEY (`idQuestion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `rang`
--

CREATE TABLE IF NOT EXISTS `rang` (
  `idRang` int(11) NOT NULL AUTO_INCREMENT,
  `nomRang` varchar(40) NOT NULL,
  PRIMARY KEY (`idRang`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `rang`
--

INSERT INTO `rang` (`idRang`, `nomRang`) VALUES
(1, 'Etudiant'),
(2, 'Proffesseur'),
(3, 'Administrateur');

-- --------------------------------------------------------

--
-- Structure de la table `recompense`
--

CREATE TABLE IF NOT EXISTS `recompense` (
  `idRecompense` int(11) NOT NULL AUTO_INCREMENT,
  `idNiveau` int(11) NOT NULL,
  `nomRecompense` varchar(25) NOT NULL,
  `contenuRecompense` varchar(100) NOT NULL,
  PRIMARY KEY (`idRecompense`),
  KEY `idNiveau` (`idNiveau`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `semestre`
--

CREATE TABLE IF NOT EXISTS `semestre` (
  `idSemestre` int(11) NOT NULL AUTO_INCREMENT,
  `nomSemestre` varchar(10) NOT NULL,
  PRIMARY KEY (`idSemestre`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `semestre`
--

INSERT INTO `semestre` (`idSemestre`, `nomSemestre`) VALUES
(1, 'Semestre 1'),
(2, 'Semestre 2'),
(3, 'Semestre 3'),
(4, 'Semestre 4');

-- --------------------------------------------------------

--
-- Structure de la table `solution`
--

CREATE TABLE IF NOT EXISTS `solution` (
  `idSolution` int(11) NOT NULL AUTO_INCREMENT,
  `contenuSolution` varchar(100) NOT NULL,
  PRIMARY KEY (`idSolution`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `solution`
--

INSERT INTO `solution` (`idSolution`, `contenuSolution`) VALUES
(1, 'Web/RESSOURCES/S1_M1_CH1_CORREC.pdf'),
(2, 'Web/RESSOURCES/S1_M1_CH2_CORREC.pdf');

-- --------------------------------------------------------

--
-- Structure de la table `typenews`
--

CREATE TABLE IF NOT EXISTS `typenews` (
  `idType` int(11) NOT NULL AUTO_INCREMENT,
  `nomType` varchar(25) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `typenews`
--

INSERT INTO `typenews` (`idType`, `nomType`) VALUES
(1, 'DS'),
(2, 'Soutiens'),
(3, 'Cours');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `chapitre`
--
ALTER TABLE `chapitre`
  ADD CONSTRAINT `fk_idModule_chapitre` FOREIGN KEY (`idModule`) REFERENCES `module` (`idModule`);

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `fk_idNiveau_compte` FOREIGN KEY (`idNiveau`) REFERENCES `niveau` (`idNiveau`),
  ADD CONSTRAINT `fk_idRang_compte` FOREIGN KEY (`idRang`) REFERENCES `rang` (`idRang`);

--
-- Contraintes pour la table `cours`
--
ALTER TABLE `cours`
  ADD CONSTRAINT `fk_idChapitre_cours` FOREIGN KEY (`idChapitre`) REFERENCES `chapitre` (`idChapitre`);

--
-- Contraintes pour la table `exercice`
--
ALTER TABLE `exercice`
  ADD CONSTRAINT `fk_idChapitre_exercice` FOREIGN KEY (`idChapitre`) REFERENCES `chapitre` (`idChapitre`);

--
-- Contraintes pour la table `ex_quest_indic_sol`
--
ALTER TABLE `ex_quest_indic_sol`
  ADD CONSTRAINT `fk_IdExo` FOREIGN KEY (`idExercice`) REFERENCES `exercice` (`idExo`),
  ADD CONSTRAINT `fk_IdIndice` FOREIGN KEY (`idIndice`) REFERENCES `indice` (`idIndice`),
  ADD CONSTRAINT `fk_IdQuestion` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`),
  ADD CONSTRAINT `fk_IdSoliution` FOREIGN KEY (`idSolution`) REFERENCES `solution` (`idSolution`);

--
-- Contraintes pour la table `module`
--
ALTER TABLE `module`
  ADD CONSTRAINT `fk_idSemestre_module` FOREIGN KEY (`idSemestre`) REFERENCES `semestre` (`idSemestre`);

--
-- Contraintes pour la table `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `fk_idCompte_news` FOREIGN KEY (`idCompte`) REFERENCES `compte` (`idCompte`),
  ADD CONSTRAINT `fk_typeNews_news` FOREIGN KEY (`typeNews`) REFERENCES `typenews` (`idType`);

--
-- Contraintes pour la table `recompense`
--
ALTER TABLE `recompense`
  ADD CONSTRAINT `fk_idNiveau_recompense` FOREIGN KEY (`idNiveau`) REFERENCES `niveau` (`idNiveau`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
