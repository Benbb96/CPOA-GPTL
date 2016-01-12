-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 06 Janvier 2016 à 10:32
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `hebergement`
--

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE IF NOT EXISTS `compte` (
  `idCompte` int(11) NOT NULL,
  `nomCompte` varchar(100) NOT NULL,
  `prenomCompte` varchar(100) NOT NULL,
  `mailCompte` varchar(100) NOT NULL,
  `mdpCompte` varchar(100) NOT NULL,
  `cle` varchar(2) NOT NULL,
  `dateCreation` date NOT NULL,
  PRIMARY KEY (`idCompte`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `compte`
--

INSERT INTO `compte` (`idCompte`, `nomCompte`, `prenomCompte`, `mailCompte`, `mdpCompte`, `cle`, `dateCreation`) VALUES
(1, 'Florentin', 'Calvin', 'calvin.florentin@etu.univ-lyon1.fr', 'bbbzUZodhqYSw', 'bb', '2015-12-17'),
(2, 'Bernard-Bouissières', 'Benjamin', 'benjamin.bernard-bouissieres@etu.univ-lyon1.fr', 'bbbzUZodhqYSw', 'bb', '2015-12-10');

-- --------------------------------------------------------

--
-- Structure de la table `hotel_arbitres`
--

CREATE TABLE IF NOT EXISTS `hotel_arbitres` (
  `id_Ahotel` int(11) NOT NULL DEFAULT '0',
  `nom` varchar(100) NOT NULL,
  `type` varchar(40) NOT NULL,
  `nbr_étoiles` int(11) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `code_postal` int(11) NOT NULL,
  `ville` varchar(100) NOT NULL,
  `nbr_lits_dispo` int(11) NOT NULL,
  `bar` varchar(3) NOT NULL,
  `restaurant` varchar(3) NOT NULL,
  `petit_déjeuner` varchar(3) NOT NULL,
  `sauna` varchar(3) NOT NULL,
  `salle_de_sport` varchar(3) NOT NULL,
  `coiffeur` varchar(3) NOT NULL,
  `pressing` varchar(3) NOT NULL,
  `hammam` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `hotel_arbitres`
--

INSERT INTO `hotel_arbitres` (`id_Ahotel`, `nom`, `type`, `nbr_étoiles`, `adresse`, `code_postal`, `ville`, `nbr_lits_dispo`, `bar`, `restaurant`, `petit_déjeuner`, `sauna`, `salle_de_sport`, `coiffeur`, `pressing`, `hammam`) VALUES
(1, 'Clos de l''herminier', 'Chambre d''''hote', 2, '25 avenue du Moulin de Tourtourel', 34880, 'Lavérune', 7, 'OUI', 'OUI', 'NON', 'NON', 'NON', 'NON', 'NON', 'NON'),
(2, 'Hôtel Le Jardin Des Sens', 'hotel', 4, '11 Avenue Saint-Lazare', 34000, 'Montpelllier', 86, 'NON', 'OUI', 'OUI', 'OUI', 'OUI', 'OUI', 'OUI', 'NON'),
(3, '5 Hotel des Arceaux', 'Appart-Hotel', 3, '25 Boulevard des Arceaux', 34000, 'Montpellier', 100, 'OUI', 'OUI', 'OUI', 'OUI', 'NON', 'OUI', 'OUI', 'OUI');

-- --------------------------------------------------------

--
-- Structure de la table `hotel_joueurs`
--

CREATE TABLE IF NOT EXISTS `hotel_joueurs` (
  `id_Jhotel` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `type` varchar(40) NOT NULL,
  `nbr_étoiles` int(11) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `code_postal` int(11) NOT NULL,
  `ville` varchar(100) NOT NULL,
  `nbr_lits_dispo` int(11) NOT NULL,
  `bar` varchar(3) NOT NULL,
  `restaurant` varchar(3) NOT NULL,
  `petit_déjeuner` varchar(3) NOT NULL,
  `sauna` varchar(3) NOT NULL,
  `salle_de_sport` varchar(3) NOT NULL,
  `coiffeur` varchar(3) NOT NULL,
  `pressing` varchar(3) NOT NULL,
  `hammam` varchar(3) NOT NULL,
  PRIMARY KEY (`id_Jhotel`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `hotel_joueurs`
--

INSERT INTO `hotel_joueurs` (`id_Jhotel`, `nom`, `type`, `nbr_étoiles`, `adresse`, `code_postal`, `ville`, `nbr_lits_dispo`, `bar`, `restaurant`, `petit_déjeuner`, `sauna`, `salle_de_sport`, `coiffeur`, `pressing`, `hammam`) VALUES
(1, 'La Charmance', 'Chambre d''hote', 2, '5 Rue du Lapin', 34970, 'Lattes', 10, 'NON', 'NON', 'OUI', 'OUI', 'NON', 'NON', 'NON', 'NON'),
(2, 'Ibis', 'Hotel', 3, '95 Place Vauban Boulevard ', 34000, 'Montpellier', 86, 'OUI', 'NON', 'OUI', 'NON', 'OUI', 'NON', 'OUI', 'NON'),
(3, 'Domaine De Verchant', 'Hotel', 5, '1 Bd Philippe Lamour', 34170, 'Castelnau-le-Lez', 150, 'OUI', 'OUI', 'OUI', 'OUI', 'OUI', 'OUI', 'OUI', 'OUI'),
(4, 'Hotel Courtyard', 'Hotel', 4, '105 Place Georges Freche (Rue du Chelia)', 34000, 'Montpellier', 80, 'OUI', 'OUI', 'OUI', 'OUI', 'OUI', 'OUI', 'NON', 'NON'),
(5, 'Hotel Balladins', 'Hotel', 3, 'Route de Carnon, Parc des Expos', 34470, 'Perols', 64, 'NON', 'OUI', 'OUI', 'NON', 'NON', 'OUI', 'OUI', 'OUI');

-- --------------------------------------------------------

--
-- Structure de la table `operations`
--

CREATE TABLE IF NOT EXISTS `operations` (
  `idOperations` int(11) NOT NULL,
  `idCompte` int(11) NOT NULL,
  `typeOperations` int(11) NOT NULL,
  `titreOperations` varchar(40) NOT NULL,
  `contenuOperations` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `dateOperations` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `operations`
--

INSERT INTO `operations` (`idOperations`, `idCompte`, `typeOperations`, `titreOperations`, `contenuOperations`, `dateOperations`) VALUES
(1, 1, 1, 'Reservation Joueur', '<p> Ici le contenu ta mere</p>', '0000-00-00'),
(2, 2, 2, 'Reservation Arbitre', '<p> Ici le contenu ta mere</p>', '0000-00-00');

-- --------------------------------------------------------

--
-- Structure de la table `typeoperations`
--

CREATE TABLE IF NOT EXISTS `typeoperations` (
  `idType` int(11) NOT NULL AUTO_INCREMENT,
  `nomType` varchar(40) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `typeoperations`
--

INSERT INTO `typeoperations` (`idType`, `nomType`) VALUES
(1, 'Reserver un logement à un joueur'),
(2, 'Reserver un logement à un arbitre');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
