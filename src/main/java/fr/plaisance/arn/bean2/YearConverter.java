package fr.plaisance.arn.bean2;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;
import org.apache.commons.lang3.StringUtils;

public class YearConverter implements Converter<String> {

    @Override
    public void serialize(String object, ObjectWriter writer, Context ctx) throws Exception {
        writer.writeString(object);
    }

    @Override
    public String deserialize(ObjectReader reader, Context ctx) throws Exception {
        String value = reader.valueAsString();
        if (StringUtils.length(value) > 3) {
            return value.substring(0, 4);
        }
        return value;
    }
}
