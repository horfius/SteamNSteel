package mod.steamnsteel.api.voxbox;

import com.google.common.base.Optional;

/**
 * Created by Bret 'Horfius' Dusseault on 9/23/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public enum VoxboxEntryManager {
    INSTANCE;

    /**
     * The singleton instance of the {@link IVoxBoxEntryManager} API. If the API is present without the mod,
     * {@link Optional#of(Object)} will return <code>false</code>.
     */
    public static Optional<IVoxBoxEntryManager> instance = Optional.absent();
}
