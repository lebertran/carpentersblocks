/*
 * This file is part of Carpenter's Blocks.
 *
 * Carpenter's Blocks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Carpenter's Blocks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Carpenter's Blocks.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package tk.eichler.carpentersblocks.data;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BaseData {
    private final Set<DataUpdateListener> listeners = new HashSet<>();
    private boolean hasChanged;

    protected void setChanged(final boolean changed) {
        this.hasChanged = changed;
    }

    public void addUpdateListener(final DataUpdateListener listener) {
        listeners.add(listener);
    }

    private void onDataUpdated() {
        this.listeners.forEach(DataUpdateListener::onDataUpdate);
    }

    public void checkForChanges() {
        if (this.hasChanged) {
            this.setChanged(false);

            onDataUpdated();
        }
    }
}
