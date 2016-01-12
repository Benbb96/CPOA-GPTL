<?php
    class Operations{
        private $_id,$_author,$_type,$_title,$_content,$_date;
        
        public function __construct($id,$author,$type,$title,$content,$date){
            $bdd = ConnexionBD::getInstance();
            
            $this->_id = $id;
            
            $nameAuthor = $bdd->getData("SELECT nomCompte, prenomCompte FROM compte WHERE idCompte=$author");
            $data = $nameAuthor->fetch(PDO::FETCH_OBJ);
            
            $this->_author = strtoupper($data->nomCompte)." ".ucfirst($data->prenomCompte);
            
            $nameType = $bdd->getData("SELECT nomType FROM typeoperations WHERE idType=$type");
            $data = $nameType->fetch(PDO::FETCH_OBJ);
            
            $this->_type = $data->nomType;
            
            $this->_title = $title;
            $this->_content = $content;
            $this->_date = $date;
        }
        
        public function getId(){
            return $this->_id;
        }
        
        public function getAuthor(){
            return $this->_author;
        }
        
        public function getType(){
            return $this->_type;
        }
        
        public function getTitle(){
            return $this->_title;
        }
        
        public function getContent(){
            return $this->_content;
        }
        
        public function getDate(){
            return $this->_date;
        }
    }

    class TypeOperations{
        private $_id, $_name;
        
        public function __construct($id,$name){
            $this->_id = $id;
            $this->_name = $name;
        }
        
        public function getId(){
            return $this->_id;
        }
        
        public function getName(){
            return $this->_name;
        }
    }

    class ListeOperations{
        private $_listeOperations,$_listeType;
        
        public function __construct(){
            $bdd = ConnexionBD::getInstance();
            
            $operations = $bdd->getData("SELECT * FROM operations");
            $i = 0;
            while($data = $operations->fetch(PDO::FETCH_OBJ)){
                $result = new Operations($data->idOperations,$data->idCompte,$data->typeOperations,$data->titreOperations,$data->contenuOperations,$data->dateOperations);
                $this->_listeOperations[$i]=$result;
                $i+=1;
            }
            
            $type = $bdd->getData("SELECT * FROM typeoperations");
            $i = 0;
            while($data = $type->fetch(PDO::FETCH_OBJ)){
                $result = new TypeOperations($data->idType,$data->nomType);
                $this->_listeType[$i]=$result;
                $i+=1;
            }
        }
        
        public function afficherListe(){
            echo "<div id='filterNews'><div class='transparent_button'>Général</div>";
            foreach($this->_listeType as $type){
                echo "<div class='transparent_button'>".$type->getName()."</div>";
            }
            echo "</div><div id='ensembleOperations'>";
            foreach($this->_listeOperations as $operations){
                echo "<div type='".$operations->getType()."' class='blocNews'>
                        <h1>".$operations->getTitle()."<span>".$operations->getAuthor()." | ".$operations->getDate()."</span></h1>
                        <p>".$operations->getContent()."</p>
                    </div>";
            }
            echo "</div>";
        }
    }
?>