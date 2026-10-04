<?php global $prefix, $submenu_editor;
include('../db.php');

$op = @$_GET['op'];

if ($op == 'check-0.3.0') {
  include "check-0.3.0.txt";
} else if ($op == 'addonfile') {
  global $db_link;
  $module_id = $db_link -> real_escape_string(@$_GET['id']);
  $rows = DB_Query('SELECT addon_rel.file FROM addon INNER JOIN addon_rel ON addon_rel.addon_id = addon.id AND NOT EXISTS(SELECT 1 FROM addon_rel rl WHERE rl.addon_id = addon_rel.addon_id AND rl.sequence > addon_rel.sequence) WHERE addon.module_id = \''.$module_id.'\'');
  $row = DB_Row();
  if ($row) {
  	echo $row['file'];
  }
  DB_Close();
} else if ($op == 'addondep') {
  global $db_link;
  $module_id = $db_link -> real_escape_string(@$_GET['id']);
  header('Content-Type: text/xml');
  echo "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
  echo "<result>\n";

  $rows = DB_Query('SELECT addon.id, addon.module_id, addon.name, addon_dep.id AS dep_id, addon_dep.type AS dep_type, addon_dep.value AS dep_value, license.id AS license, license.spdx, license.name AS license_name, license.file AS license_file FROM addon INNER JOIN addon_rel ON addon_rel.addon_id = addon.id AND NOT EXISTS(SELECT 1 FROM addon_rel rl WHERE rl.addon_id = addon_rel.addon_id AND rl.sequence > addon_rel.sequence) LEFT JOIN license ON license.id = addon_rel.license_id LEFT JOIN addon_dep ON addon_dep.addon_rel_id = addon_rel.id WHERE addon.module_id = \''.$module_id.'\' ORDER BY addon_dep.id');
  $addon_id = 0;
  $dep_id = 0;
  while ($row = DB_Row()) {
    if ($row['id'] != $addon_id) {
      if ($addon_id != 0) {
        if ($dep_id != 0) {
          echo "    </dependency>\n";
        }
        echo "  </module>\n";
      }
      echo "  <module id=\"".$row['module_id']."\" name=\"".$row['name']."\">\n";
      $addon_id = $row['id'];
      $dep_id = 0;
      if ($row['license']) {
        $attr = "";
        if ($row['spdx']) {
          $attr = " spdx=\"".$row['spdx']."\"";
        }
        if ($row['license_file']) {
          $attr .= " file=\"".$row['license_file']."\"";
        }
        echo "    <license".$attr.">".$row['license_name']."</license>\n";
      }
    }
    if ($row['dep_id']) {
      if ($dep_id == 0) {
        echo "    <dependency>\n";
      }
      $dep_id = $row['dep_id'];
      $type = $row['dep_type'];
      if ($type == 0) {
        echo "      <module id=\"".$row['dep_value']."\" />\n";
      } else if ($type == 1) {
        echo "      <library jar=\"".$row['dep_value']."\" />\n";
      } else if ($type == 2) {
        echo "      <library maven=\"".$row['dep_value']."\" />\n";
      }
    }
  }
  if ($addon_id != 0) {
    if ($dep_id != 0) {
      echo "    </dependency>\n";
    }
    echo "  </module>\n";
  }

  DB_Close();

  echo "</result>\n";
} else if ($op == 'list' || $op == 'search') {
  header('Content-Type: text/xml');
  echo "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
  echo "<result>\n";

  $condition = '';
  if ($op == 'search') {
  	  $condition = ' WHERE addon.name LIKE \'%'.$db_link -> real_escape_string(@$_GET['query']).'%\'';
  }
  $rows = DB_Query('SELECT addon.id, addon.module_id, addon.name, addon.description, addon.homepage, addon.icon, addon.created, addon.owner_id, addon.created, addon_dep.id AS dep_id, addon_dep.type AS dep_type, addon_dep.value AS dep_value, addon_rel.version, user.name AS provider, license.id AS license, license.spdx, license.name AS license_name, license.file AS license_file FROM addon INNER JOIN user ON user.id = addon.owner_id INNER JOIN addon_rel ON addon_rel.addon_id = addon.id AND NOT EXISTS(SELECT 1 FROM addon_rel rl WHERE rl.addon_id = addon_rel.addon_id AND rl.sequence > addon_rel.sequence) LEFT JOIN license ON license.id = addon_rel.license_id LEFT JOIN addon_dep ON addon_dep.addon_rel_id = addon_rel.id'.$condition.' ORDER BY addon.id, addon_dep.id');
  $addon_id = 0;
  $dep_id = 0;
  while ($row = DB_Row()) {
    if ($row['id'] != $addon_id) {
      if ($addon_id != 0) {
        if ($dep_id != 0) {
          echo "    </dependency>\n";
        }
        echo "  </module>\n";
      }
      echo "  <module id=\"".$row['module_id']."\" name=\"".$row['name']."\">\n";
      $addon_id = $row['id'];
      $dep_id = 0;
      if ($row['description']) {
        echo "    <description>".$row['description']."</description>\n";
      }
      if ($row['version']) {
        echo "    <version>".$row['version']."</version>\n";
      }
      if ($row['license']) {
        $attr = "";
        if ($row['spdx']) {
          $attr = " spdx=\"".$row['spdx']."\"";
        }
        if ($row['license_file']) {
          $attr .= " file=\"".$row['license_file']."\"";
        }
        echo "    <license".$attr.">".$row['license_name']."</license>\n";
      }
      if ($row['provider']) {
        echo "    <provider>".$row['provider']."</provider>\n";
      }
      if ($row['homepage']) {
        echo "    <homepage>".$row['homepage']."</homepage>\n";
      }
      if ($row['icon']) {
        echo "    <icon>".$row['icon']."</icon>\n";
      }
    }
    if ($row['dep_id']) {
      if ($dep_id == 0) {
        echo "    <dependency>\n";
      }
      $dep_id = $row['dep_id'];
      $type = $row['dep_type'];
      if ($type == 0) {
        echo "      <module id=\"".$row['dep_value']."\" />\n";
      } else if ($type == 1) {
        echo "      <library jar=\"".$row['dep_value']."\" />\n";
      } else if ($type == 2) {
        echo "      <library maven=\"".$row['dep_value']."\" />\n";
      }
    }
  }
  if ($addon_id != 0) {
    if ($dep_id != 0) {
      echo "    </dependency>\n";
    }
    echo "  </module>\n";
  }

  DB_Close();

  echo "</result>\n";
} else if ($op == 'updates') {
  $rows = DB_Query('SELECT addon.module_id, addon_rel.version FROM addon INNER JOIN addon_rel ON addon_rel.addon_id = addon.id AND NOT EXISTS(SELECT 1 FROM addon_rel rl WHERE rl.addon_id = addon_rel.addon_id AND rl.sequence > addon_rel.sequence) ORDER BY addon.id');
  while ($row = DB_Row()) {
  	echo $row['module_id'].':'.$row['version']."\n";
  }
  DB_Close();
} else if ($op == 'addondetail') {
  $module_id = str_replace('/','', str_replace('\\','', @$_GET['id']));
  $textfile = '../text/'.$module_id.'.dat';
  if (file_exists($textfile)) {
    include $textfile;
  }
}
?>