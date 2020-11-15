package com.arc.zero.service.app;

import com.arc.core.model.domain.app.AppContact;
import com.arc.core.model.request.app.AppContactRequest;

import java.util.List;
import java.util.Set;

/**
 * APP联系人同步
 *
 * @author lamy
 * @since
 */
public interface SyncAppContactService {

    /**
     * 同步
     *
     * @param request
     * @return
     */
    Set<AppContact> sync(AppContactRequest request);
}
