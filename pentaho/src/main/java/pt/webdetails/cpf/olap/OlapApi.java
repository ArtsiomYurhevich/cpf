/*!
* Copyright 2002 - 2017 Webdetails, a Hitachi Vantara company.  All rights reserved.
*
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

package pt.webdetails.cpf.olap;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.json.JSONException;

@Path( "/{pluginId}/api/olap" )
public class OlapApi {
  private static final Log logger = LogFactory.getLog( OlapApi.class );

  @GET
  @Path( "/getCubes" )
  @Produces( "text/javascript" )
  public void getCubes( @Context HttpServletResponse response ) throws IOException, JSONException {
    OlapUtils olapUtils = new OlapUtils();
    JSONObject result = olapUtils.getOlapCubes();
    buildJsonResult( response.getOutputStream(), result != null, result );
  }

  @GET
  @Path( "/getCubeStructure" )
  @Produces( "text/javascript" )
  public void getCubeStructure( @QueryParam( MethodParams.CATALOG ) String catalog,
      @QueryParam( MethodParams.CUBE ) String cube,
      @QueryParam( MethodParams.JNDI ) String jndi,
      @Context HttpServletResponse response ) throws IOException, JSONException {
    OlapUtils olapUtils = new OlapUtils();
    JSONObject result = olapUtils.getCubeStructure( catalog, cube, jndi );
    buildJsonResult( response.getOutputStream(), result != null, result );
  }

  @GET
  @Path( "/getLevelMembersStructure" )
  @Produces( "text/javascript" )
  public void getLevelMembersStructure( @QueryParam( MethodParams.CATALOG ) String catalog,
      @QueryParam( MethodParams.CUBE ) String cube,
      @QueryParam( MethodParams.MEMBER ) String member,
      @QueryParam( MethodParams.DIRECTION ) String direction,
      @Context HttpServletResponse response )
      throws IOException, JSONException {
    OlapUtils olapUtils = new OlapUtils();
    JSONObject result = olapUtils.getLevelMembersStructure( catalog, cube, member, direction );
    buildJsonResult( response.getOutputStream(), result != null, result );
  }

  @GET
  @Path( "/getPaginatedLevelMembers" )
  @Produces( "text/javascript" )
  public void getPaginatedLevelMembers( @QueryParam( MethodParams.CATALOG ) String catalog,
      @QueryParam( MethodParams.CUBE ) String cube,
      @QueryParam( MethodParams.LEVEL ) String level,
      @QueryParam( MethodParams.START_MEMBER ) String startMember,
      @QueryParam( MethodParams.CONTEXT ) String context,
      @QueryParam( MethodParams.SEARCH_TERM ) String searchTerm,
      @QueryParam( MethodParams.PAGE_SIZE ) long pageSize,
      @QueryParam( MethodParams.PAGE_START ) long pageStart,
      @Context HttpServletResponse response ) throws IOException, JSONException {
    OlapUtils olapUtils = new OlapUtils();
    JSONObject result = olapUtils
        .getPaginatedLevelMembers( catalog, cube, level, startMember, context, searchTerm, pageSize, pageStart );
    buildJsonResult( response.getOutputStream(), result != null, result );

  }

  private class MethodParams {
    public static final String CATALOG = "catalog";
    public static final String CUBE = "cube";
    public static final String LEVEL = "level";
    public static final String JNDI = "jndi";
    public static final String MEMBER = "member";
    public static final String START_MEMBER = "startMember";
    public static final String DIRECTION = "direction";
    public static final String CONTEXT = "context";
    public static final String SEARCH_TERM = "searchTerm";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_START = "pageStart";
  }

  private void buildJsonResult( OutputStream out, Boolean sucess, Object result ) throws JSONException {
    JSONObject jsonResult = new JSONObject();

    jsonResult.put( "status", sucess.toString() );
    if ( result != null ) {
      jsonResult.put( "result", result );
    }
    PrintWriter pw = null;
    try {
      pw = new PrintWriter( out );
      pw.print( jsonResult.toString( 2 ) );
      pw.flush();
    } finally {
      IOUtils.closeQuietly( pw );
    }

  }

}
