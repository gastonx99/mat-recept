<?php
 /* connect to the db */
  $link = mysql_connect('localhost','matrecept','Milano93') or die('Cannot connect to the DB');
  mysql_select_db('matrecept',$link) or die('Cannot select the DB');
  
  $query = "SELECT xml FROM menues WHERE username = 'gaston'";
  $result = mysql_query($query,$link) or die('Errant query:  '.$query);  
  $post = mysql_fetch_assoc($result);
  
  header('Content-type: text/xml');
  if(is_array($post)) {
  	foreach($post as $key => $value) {
  		echo $value;
  	}
  }
  
  /* disconnect from the db */
  @mysql_close($link);  
?>