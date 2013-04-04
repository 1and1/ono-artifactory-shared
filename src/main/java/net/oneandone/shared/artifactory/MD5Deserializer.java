/**
 * Copyright 1&1 Internet AG, https://github.com/1and1/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oneandone.shared.artifactory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.oneandone.shared.artifactory.model.MD5;
import java.lang.reflect.Type;

/**
 *
 * @author Mirko Friedenhagen
 */
public class MD5Deserializer implements JsonDeserializer<MD5> {

    @Override
    public MD5 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return MD5.valueOf(json.getAsString());
    }
    
}
