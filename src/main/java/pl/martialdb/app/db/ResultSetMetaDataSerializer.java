/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      ResultSetSerializer.java
 *
 *  DESCRIPTION
 *      ResultSetSerializer class
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  25-Apr-2014  Initial
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.db;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class ResultSetMetaDataSerializer extends JsonSerializer<ResultSetMetaData> {

    public static class ResultSetSerializerException extends JsonProcessingException{
        private static final long serialVersionUID = -914957626413580736L;

        public ResultSetSerializerException(Throwable cause){
            super(cause);
        }
    }

    @Override
    public Class<ResultSetMetaData> handledType() {
        return ResultSetMetaData.class;
    }

    @Override
    public void serialize(ResultSetMetaData rsmd, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        try {

            jgen.writeStartArray();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                jgen.writeStartObject();
                jgen.writeFieldName(rsmd.getColumnLabel(i + 1));
                jgen.writeNumber(rsmd.getColumnType(i + 1));
                jgen.writeEndObject();
            }
            jgen.writeEndArray();

        } catch (SQLException e) {
            throw new ResultSetSerializerException(e);
        }
    }
}