<?php

 /* connect to the db */
  $link = mysql_connect('localhost','dandel_se','M1l@n093') or die('Cannot connect to the DB');
  mysql_select_db('dandel_se',$link) or die('Cannot select the DB');
  

?>