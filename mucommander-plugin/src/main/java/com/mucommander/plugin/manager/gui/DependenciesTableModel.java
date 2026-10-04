/*
 * This file is part of muCommander, http://www.mucommander.com
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mucommander.plugin.manager.gui;

import com.mucommander.plugin.DependencyRecord;
import com.mucommander.plugin.manager.LanguageUtils;
import java.util.List;
import java.util.ResourceBundle;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import javax.swing.table.AbstractTableModel;

/**
 * Addon dependencies table model.
 */
@NullMarked
public class DependenciesTableModel extends AbstractTableModel {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(DependenciesTableModel.class));

    protected @Nullable List<DependencyRecord> dependencies;

    public DependenciesTableModel() {
    }

    public void setDependencies(@Nullable List<DependencyRecord> dependencies) {
        this.dependencies = dependencies;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return dependencies == null ? 0 : dependencies.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return resourceBundle.getString("typeColumn.name");
            case 1:
                return resourceBundle.getString("valueColumn.name");
            case 2:
                return resourceBundle.getString("optionalColumn.name");
            default:
                throw new AssertionError();
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DependencyRecord record = dependencies.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return resourceBundle.getString("dependencyType." + record.getType().name().toLowerCase());
            case 1:
                return record.getId();
            case 2:
                return record.isOptional();
            default:
                throw new AssertionError();
        }
    }
}
