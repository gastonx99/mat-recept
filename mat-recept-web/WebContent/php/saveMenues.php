<?php
  require_once 'KLogger.php';
  
  
  $log = new KLogger ( "log.log" , KLogger::DEBUG );
  
  
  $username = $_REQUEST["username"];
  
  $xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".$_REQUEST["xml"];
  $log->LogDebug("Username: $username");

  /*
  $req_dump = print_r($_REQUEST, TRUE);
  $log->LogDebug($req_dump);
  */
  
 /* connect to the db */
  $link = mysql_connect('localhost','matrecept','Milano93') or die('Cannot connect to the DB');
  mysql_select_db('matrecept',$link) or die('Cannot select the DB');
  
  $query = "UPDATE menues SET xml = '$xml' WHERE username = '$username'";
  $log->LogDebug($query);

  $result = mysql_query($query,$link) or die('Errant query:  '.$query);  
  
  /* disconnect from the db */
  @mysql_close($link);  
  
  header("HTTP/1.0 204 No Response");
  
?>