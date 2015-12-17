<?php
    class ConnexionBD{
        private static $_connexion, $_instance;
        
        private function __construct(){
            self::$_connexion = new PDO('mysql:host=localhost;dbname=hebergement', 'root', '');
        }
        
        private function __clone(){}
        
        public static function getInstance(){
            if(!isset(self::$_instance)) self::$_instance = new self;
            return self::$_instance;
        }

        public function getData($requete){
            $result = self::$_connexion->prepare($requete);
            $result->execute();
            return $result;
        }

        public function setData($requete){
            $result = self::$_connexion->prepare($requete);
            $result->execute();
        }
        
        public function getLastId(){
            return self::$_connexion->lastInsertId();
        }
    }
?>