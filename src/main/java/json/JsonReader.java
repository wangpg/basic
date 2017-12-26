package json;

import java.util.Stack;

public class JsonReader {

    TokenReader reader;

    public Object parse() {
//        Stack stack = new Stack();
//        int status = STATUS_EXPECT_SINGLE_VALUE | STATUS_EXPECT_BEGIN_OBJECT | STATUS_EXPECT_BEGIN_ARRAY;
//        for (;;) {
//            Token currentToken = reader.readNextToken();
//            switch (currentToken) {
//            case BOOLEAN:
//                if (hasStatus(STATUS_EXPECT_SINGLE_VALUE)) {
//                    // single boolean:
//                    Boolean bool = reader.readBoolean();
//                    stack.push(StackValue.newJsonSingle(bool));
//                    status = STATUS_EXPECT_END_DOCUMENT;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_OBJECT_VALUE)) {
//                    Boolean bool = reader.readBoolean();
//                    String key = stack.pop(StackValue.TYPE_OBJECT_KEY).valueAsKey();
//                    stack.peek(StackValue.TYPE_OBJECT).valueAsObject().put(key, bool);
//                    status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_OBJECT;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_ARRAY_VALUE)) {
//                    Boolean bool = reader.readBoolean();
//                    stack.peek(StackValue.TYPE_ARRAY).valueAsArray().add(bool);
//                    status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_ARRAY;
//                    continue;
//                }
//                throw new JsonParseException("Unexpected boolean.", reader.reader.readed);
//
//            case NULL:
//                if (hasStatus(STATUS_EXPECT_SINGLE_VALUE)) {
//                    // single null:
//                    reader.readNull();
//                    stack.push(StackValue.newJsonSingle(null));
//                    status = STATUS_EXPECT_END_DOCUMENT;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_OBJECT_VALUE)) {
//                    reader.readNull();
//                    String key = stack.pop(StackValue.TYPE_OBJECT_KEY).valueAsKey();
//                    stack.peek(StackValue.TYPE_OBJECT).valueAsObject().put(key, null);
//                    status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_OBJECT;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_ARRAY_VALUE)) {
//                    reader.readNull();
//                    stack.peek(StackValue.TYPE_ARRAY).valueAsArray().add(null);
//                    status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_ARRAY;
//                    continue;
//                }
//                throw new JsonParseException("Unexpected null.", reader.reader.readed);
//
//            case NUMBER:
//                if (hasStatus(STATUS_EXPECT_SINGLE_VALUE)) {
//                    // single number:
//                    Number number = reader.readNumber();
//                    stack.push(StackValue.newJsonSingle(number));
//                    status = STATUS_EXPECT_END_DOCUMENT;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_OBJECT_VALUE)) {
//                    Number number = reader.readNumber();
//                    String key = stack.pop(StackValue.TYPE_OBJECT_KEY).valueAsKey();
//                    stack.peek(StackValue.TYPE_OBJECT).valueAsObject().put(key, number);
//                    status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_OBJECT;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_ARRAY_VALUE)) {
//                    Number number = reader.readNumber();
//                    stack.peek(StackValue.TYPE_ARRAY).valueAsArray().add(number);
//                    status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_ARRAY;
//                    continue;
//                }
//                throw new JsonParseException("Unexpected number.", reader.reader.readed);
//
//            case STRING:
//                if (hasStatus(STATUS_EXPECT_SINGLE_VALUE)) {
//                    // single string:
//                    String str = reader.readString();
//                    stack.push(StackValue.newJsonSingle(str));
//                    status = STATUS_EXPECT_END_DOCUMENT;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_OBJECT_KEY)) {
//                    String str = reader.readString();
//                    stack.push(StackValue.newJsonObjectKey(str));
//                    status = STATUS_EXPECT_COLON;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_OBJECT_VALUE)) {
//                    String str = reader.readString();
//                    String key = stack.pop(StackValue.TYPE_OBJECT_KEY).valueAsKey();
//                    stack.peek(StackValue.TYPE_OBJECT).valueAsObject().put(key, str);
//                    status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_OBJECT;
//                    continue;
//                }
//                if (hasStatus(STATUS_EXPECT_ARRAY_VALUE)) {
//                    String str = reader.readString();
//                    stack.peek(StackValue.TYPE_ARRAY).valueAsArray().add(str);
//                    status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_ARRAY;
//                    continue;
//                }
//                throw new JsonParseException("Unexpected char \'\"\'.", reader.reader.readed);
//
//            case SEP_COLON: // :
//                if (status == STATUS_EXPECT_COLON) {
//                    status = STATUS_EXPECT_OBJECT_VALUE | STATUS_EXPECT_BEGIN_OBJECT | STATUS_EXPECT_BEGIN_ARRAY;
//                    continue;
//                }
//                throw new JsonParseException("Unexpected char \':\'.", reader.reader.readed);
//
//            case SEP_COMMA: // ,
//                if (hasStatus(STATUS_EXPECT_COMMA)) {
//                    if (hasStatus(STATUS_EXPECT_END_OBJECT)) {
//                        status = STATUS_EXPECT_OBJECT_KEY;
//                        continue;
//                    }
//                    if (hasStatus(STATUS_EXPECT_END_ARRAY)) {
//                        status = STATUS_EXPECT_ARRAY_VALUE | STATUS_EXPECT_BEGIN_ARRAY | STATUS_EXPECT_BEGIN_OBJECT;
//                        continue;
//                    }
//                }
//                throw new JsonParseException("Unexpected char \',\'.", reader.reader.readed);
//
//            case END_ARRAY:
//                if (hasStatus(STATUS_EXPECT_END_ARRAY)) {
//                    StackValue array = stack.pop(StackValue.TYPE_ARRAY);
//                    if (stack.isEmpty()) {
//                        stack.push(array);
//                        status = STATUS_EXPECT_END_DOCUMENT;
//                        continue;
//                    }
//                    int type = stack.getTopValueType();
//                    if (type == StackValue.TYPE_OBJECT_KEY) {
//                        // key: [ CURRENT ] ,}
//                        String key = stack.pop(StackValue.TYPE_OBJECT_KEY).valueAsKey();
//                        stack.peek(StackValue.TYPE_OBJECT).valueAsObject().put(key, array.value);
//                        status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_OBJECT;
//                        continue;
//                    }
//                    if (type == StackValue.TYPE_ARRAY) {
//                        // xx, xx, [CURRENT] ,]
//                        stack.peek(StackValue.TYPE_ARRAY).valueAsArray().add(array.value);
//                        status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_ARRAY;
//                        continue;
//                    }
//                }
//                throw new JsonParseException("Unexpected char: \']\'.", reader.reader.readed);
//
//            case END_OBJECT:
//                if (hasStatus(STATUS_EXPECT_END_OBJECT)) {
//                    StackValue object = stack.pop(StackValue.TYPE_OBJECT);
//                    if (stack.isEmpty()) {
//                        // root object:
//                        stack.push(object);
//                        status = STATUS_EXPECT_END_DOCUMENT;
//                        continue;
//                    }
//                    int type = stack.getTopValueType();
//                    if (type == StackValue.TYPE_OBJECT_KEY) {
//                        String key = stack.pop(StackValue.TYPE_OBJECT_KEY).valueAsKey();
//                        stack.peek(StackValue.TYPE_OBJECT).valueAsObject().put(key, object.value);
//                        status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_OBJECT;
//                        continue;
//                    }
//                    if (type == StackValue.TYPE_ARRAY) {
//                        stack.peek(StackValue.TYPE_ARRAY).valueAsArray().add(object.value);
//                        status = STATUS_EXPECT_COMMA | STATUS_EXPECT_END_ARRAY;
//                        continue;
//                    }
//                }
//                throw new JsonParseException("Unexpected char: \'}\'.", reader.reader.readed);
//
//            case END_DOCUMENT:
//                if (hasStatus(STATUS_EXPECT_END_DOCUMENT)) {
//                    StackValue v = stack.pop();
//                    if (stack.isEmpty()) {
//                        return v.value;
//                    }
//                }
//                throw new JsonParseException("Unexpected EOF.", reader.reader.readed);
//
//            case BEGIN_ARRAY:
//                if (hasStatus(STATUS_EXPECT_BEGIN_ARRAY)) {
//                    stack.push(StackValue.newJsonArray(this.jsonArrayFactory.createJsonArray()));
//                    status = STATUS_EXPECT_ARRAY_VALUE | STATUS_EXPECT_BEGIN_OBJECT | STATUS_EXPECT_BEGIN_ARRAY | STATUS_EXPECT_END_ARRAY;
//                    continue;
//                }
//                throw new JsonParseException("Unexpected char: \'[\'.", reader.reader.readed);
//
//            case BEGIN_OBJECT:
//                if (hasStatus(STATUS_EXPECT_BEGIN_OBJECT)) {
//                    stack.push(StackValue.newJsonObject(this.jsonObjectFactory.createJsonObject()));
//                    status = STATUS_EXPECT_OBJECT_KEY | STATUS_EXPECT_BEGIN_OBJECT | STATUS_EXPECT_END_OBJECT;
//                    continue;
//                }
//                throw new JsonParseException("Unexpected char: \'{\'.", reader.reader.readed);
//            }
//        }
    	
    	return null;
    }
}