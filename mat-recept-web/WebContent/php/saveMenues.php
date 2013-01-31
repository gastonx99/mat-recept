<?php
  require_once 'KLogger.php';
  require_once('init.php');
  
  $log = new KLogger ( "log.log" , KLogger::DEBUG );
  
  
  $username = $_REQUEST["username"];
  
  $xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".$_REQUEST["xml"];
  $log->LogDebug("Username: $username");

  /*
  $req_dump = print_r($_REQUEST, TRUE);
  $log->LogDebug($req_dump);
  */
  
  $query = "UPDATE menues SET xml = '$xml' WHERE username = '$username'";
  $log->LogDebug($query);

  $result = mysql_query($query,$link) or die('Errant query:  '.$query);  
  
  /* disconnect from the db */
  @mysql_close($link);  
  
  header("HTTP/1.0 204 No Response");
  
?>