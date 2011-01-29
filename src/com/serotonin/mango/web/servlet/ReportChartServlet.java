/*
    Mango - Open Source M2M - http://mango.serotoninsoftware.com
    Copyright (C) 2006-2011 Serotonin Software Technologies Inc.
    @author Matthew Lohbihler
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.serotonin.mango.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.serotonin.mango.Common;
import com.serotonin.mango.vo.User;

/**
 * @author Matthew Lohbihler
 */
public class ReportChartServlet extends BaseInfoServlet {
    private static final long serialVersionUID = -1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // The only place that should be calling this servlet is the report chart, generated by a Freemarker
        // template. The ReportChartCreator that controlled the generation put the generated image content into the
        // user object, so all i need to do is write that content to the response object.
        User user = Common.getUser(request);
        Map<String, byte[]> imageData = user.getReportImageData();
        if (imageData != null) {
            String path = request.getPathInfo();

            // Path will be of the format "/<chartName>", so we need to ignore the first character.
            byte[] data = imageData.get(path.substring(1));
            if (data != null)
                response.getOutputStream().write(data);
        }
    }
}