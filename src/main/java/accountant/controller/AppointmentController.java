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
package accountant.controller;

import accountant.constants.Profile;
import accountant.constants.StateOfAppointment;
import accountant.data.Notification;
import accountant.models.ui.AppointmentUi;
import accountant.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lehr0416
 *         Date: 07-Dec-16
 *         Time: 11:36
 */
@Controller
public class AppointmentController extends BaseController {

    static final String JSP_PAGE_APPOINTMENTS = "appointments";
    static final String JSP_KEY_APPOINTMENTS = "appointmentSet";
    static final String JSP_KEY_STATUS_LIST = "appointmentStatusList";

    @Autowired
    AppointmentService appointmentService;

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public ModelAndView getAll(@ModelAttribute ArrayList<Notification> notifications) {
        ModelAndView model = new ModelAndView(JSP_PAGE_APPOINTMENTS);
        defaultModelInitialize(model, notifications, "users.header");

        Set<AppointmentUi> appointmentUiSet = appointmentService.getAll();
        model.addObject(JSP_KEY_APPOINTMENTS, appointmentUiSet);

        List<String> codeOfAppointmentList = Arrays.asList(StateOfAppointment.values())
                .stream()
                .map(sA -> sA.getCode())
                .collect(Collectors.toList());

        model.addObject(JSP_KEY_STATUS_LIST, codeOfAppointmentList);

        return model;
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