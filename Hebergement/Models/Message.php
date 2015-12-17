<?php
    class Message{
        private $_titreMessage,$_contenuMessage,$_liste;
        
        public function __construct($titreMessage,$contenuMessage,$liste){
            $this->_titreMessage = $titreMessage;
            $this->_contenuMessage = "<p style='text-align:center;font-weight:bold;margin-bottom:20px;'>".$contenuMessage."</p>";
            $this->_liste = $liste;
            if($liste) $this->_contenuMessage = $this->_contenuMessage."<ul style='width:50%;margin:auto;'>";
        }
        
        public function ajouterElements($elements){
            if($this->_liste){
                foreach($elements as $element){
                    $this->_contenuMessage = $this->_contenuMessage."<li style='margin:0;padding:0;'>".$element."</li>";
                }
            }else{
                $this->_contenuMessage = $this->_contenuMessage." !! Ce message n'est pas défini en tant que liste !!";
            }            
        }
        
        public function afficher(){        
            $titreMessage = $this->_titreMessage; 
            $contenuMessage = $this->_contenuMessage;
            if($this->_liste) $contenuMessage = $contenuMessage."</ul>";
            require("../Views/view_message.php");  
        }
    }
?>