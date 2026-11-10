package dev.zac99lol.originscontent.init.apoli;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import io.github.apace100.calio.data.SerializableDataType;
import team.lodestar.lodestone.systems.easing.Easing;

public interface ModDataTypes {
    SerializableDataType<Easing> EASING = new SerializableDataType<>(
        Easing.class,
        (buf, easing) -> buf.writeString(easing.name),
        buf -> Easing.EASINGS.get(buf.readString()),
        jsonElement -> Easing.EASINGS.get(jsonElement.getAsString())
    );

    SerializableDataType<float[]> INTENSITY = new SerializableDataType<>(float[].class,
        (buf, arr) -> {
            buf.writeVarInt(arr.length);
            for (float f : arr) {
                buf.writeFloat(f);
            }
        },
        buf -> {
            int length = buf.readVarInt();
            float[] arr = new float[length];
            for (int i = 0; i < length; i++) {
                arr[i] = buf.readFloat();
            }
            return arr;
        },
        jsonElement -> {
            if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
                float value = jsonElement.getAsFloat();
                return new float[]{value};
            }

            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                int size = jsonArray.size();

                if (size < 1 || size > 5) {
                    throw new JsonSyntaxException("Expected an array of 1, 2, or 3 floats. Found " + size + "values");
                }

                float[] values = new float[size];
                for (int i = 0; i < size; i++) {
                    JsonElement element = jsonArray.get(i);
                    if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) {
                        throw new JsonSyntaxException("Array elements must be numbers");
                    }
                    values[i] = element.getAsFloat();
                }
                return values;
            }

            throw new JsonSyntaxException("Expected a float or an list of floats");
        });
}
