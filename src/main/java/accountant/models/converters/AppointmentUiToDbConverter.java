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
package accountant.models.converters;

import accountant.constants.StateOfAppointment;
import accountant.models.db.AppointmentDb;
import accountant.models.db.UserDb;
import accountant.models.ui.AppointmentUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

/**
 * @author lehr0416
 *         Date: 07-Dec-16
 *         Time: 10:39
 */
public class AppointmentUiToDbConverter implements Converter<AppointmentUi, AppointmentDb> {

    @Autowired
    private ConversionServiceFactoryBean conversionServiceFactoryBean;

    @Override
    public AppointmentDb convert(AppointmentUi src) {
        ConversionService conversionService = conversionServiceFactoryBean.getObject();
        AppointmentDb dest = new AppointmentDb();

        dest.setId(src.getId());
        dest.setCreated(src.getCreated());
        dest.setPlanned(src.getPlanned());
        dest.setPatient(conversionService.convert(src.getPatient(), UserDb.class));
        dest.setDoctor(conversionService.convert(src.getDoctor(), UserDb.class));
        dest.setPrice(src.getPrice());
        dest.setNote(src.getNote());
        dest.setState(StateOfAppointment.valueOf(src.getState().toUpperCase()).getCode());

        return dest;
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