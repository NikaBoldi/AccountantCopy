/*

 This software is the confidential information and copyrighted work of
 NetCracker Technology Corp. ("NetCracker") and/or its suppliers and
 is only distributed under the terms of a separate license agreement
 with NetCracker.
 Use of the software is governed by the terms of the license agreement.
 Any use of this software not in accordance with the license agreement
 is expressly prohibited by law, and may result in severe civil
 and criminal penalties. 
 
 Copyright (c) 1995-2016 NetCracker Technology Corp.
 
 All Rights Reserved.
 
*/
/*
 * Copyright 1995-2016 by NetCracker Technology Corp.,
 * University Office Park III
 * 95 Sawyer Road
 * Waltham, MA 02453
 * United States of America
 * All rights reserved.
 */
package accountant.service.impl;

import accountant.dao.AppointmentDao;
import accountant.models.db.AppointmentDb;
import accountant.models.db.UserDb;
import accountant.models.ui.AppointmentUi;
import accountant.service.AppointmentService;
import accountant.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lehr0416
 *         Date: 07-Dec-16
 *         Time: 11:45
 */
@Service("appointmentService")
@Transactional
public class AppointmentServiceImpl extends BaseService implements AppointmentService {

    @Autowired
    private AppointmentDao dao;

    @Override
    public void persist(AppointmentUi appointment) {
        AppointmentDb appointmentDb = convert(appointment, AppointmentDb.class);

        dao.persist(appointmentDb);
    }

    @Override
    public Set<AppointmentUi> getAll() {
        Set<AppointmentDb> appointmentDbSet = dao.getAll();
        Set<AppointmentUi> appointmentUiSet = appointmentDbSet.stream().map((a) -> convert(a, AppointmentUi.class)).collect(Collectors.toSet());
        return appointmentUiSet;
    }

    @Override
    public Set<AppointmentUi> getByDate(Date from, Date to) {
        return null;
    }

    @Override
    public Set<AppointmentUi> getByPatient(UserDb patient) {
        return null;
    }

    @Override
    public AppointmentUi findById(int id) {
        return null;
    }

    @Override
    public void update(AppointmentUi appointment) {

    }

    @Override
    public void delete(AppointmentUi appointment) {

    }
}
/*
 WITHOUT LIMITING THE FOREGOING, COPYING, REPRODUCTION, REDISTRIBUTION,
 REVERSE ENGINEERING, DISASSEMBLY, DECOMPILATION OR MODIFICATION
 OF THE SOFTWARE IS EXPRESSLY PROHIBITED, UNLESS SUCH COPYING,
 REPRODUCTION, REDISTRIBUTION, REVERSE ENGINEERING, DISASSEMBLY,
 DECOMPILATION OR MODIFICATION IS EXPRESSLY PERMITTED BY THE LICENSE
 AGREEMENT WITH NETCRACKER. 
 
 THIS SOFTWARE IS WARRANTED, IF AT ALL, ONLY AS EXPRESSLY PROVIDED IN
 THE TERMS OF THE LICENSE AGREEMENT, EXCEPT AS WARRANTED IN THE
 LICENSE AGREEMENT, NETCRACKER HEREBY DISCLAIMS ALL WARRANTIES AND
 CONDITIONS WITH REGARD TO THE SOFTWARE, WHETHER EXPRESS, IMPLIED
 OR STATUTORY, INCLUDING WITHOUT LIMITATION ALL WARRANTIES AND
 CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE,
 TITLE AND NON-INFRINGEMENT.
 
 Copyright (c) 1995-2016 NetCracker Technology Corp.
 
 All Rights Reserved.
*/