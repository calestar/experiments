/***
 * Copyright (c) 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

#define NAPI_VERSION 3
#include <node_api.h>

#include <cstdlib>
#include <cstdio>
#include <stdbool.h>

#define trace() \
    printf("> %s\n", __func__)

#define NODE_API_CALL(fct, error)             \
    status = (fct);                           \
    if (status != napi_ok) {                  \
        napi_throw_error(env, NULL, error);   \
    }

#define NCI_CLASS_NAME "NCINativeDevice"
#define NCI_CLASS_NAME_LENGTH sizeof(NCI_CLASS_NAME) - 1

#define FCT_NAME_get_whatever "get_whatever"
#define FCT_NAME_LENGTH_get_whatever sizeof(FCT_NAME_get_whatever) - 1

#define FCT_NAME_open_file "open_file"
#define FCT_NAME_LENGTH_open_file sizeof(FCT_NAME_open_file) - 1


typedef struct nci_native_device_struct {
    int32_t whatever;
    FILE* some_file;
} nci_native_device_struct;


napi_value jsTRUE;
napi_value jsFALSE;


nci_native_device_struct* new_nci_native_device(int32_t whatever) {
    nci_native_device_struct* self = reinterpret_cast<nci_native_device_struct*>(malloc(sizeof(nci_native_device_struct)));
    trace();

    self->whatever = whatever;

    return self;
}

void free_nci_native_device(napi_env env, void* finalize_data, void* finalize_hint) {
    nci_native_device_struct* self = reinterpret_cast<nci_native_device_struct*>(finalize_data);
    trace();

    if (NULL != self->some_file) {
        fclose(self->some_file);
        printf(">> Closed some_file\n");
        self->some_file = NULL;
    }

    free(self);
}

napi_value nci_native_device_get_whatever(napi_env env, napi_callback_info info) {
    napi_status status;
    size_t argc = 0;
    napi_value jsthis;
    napi_value result;
    nci_native_device_struct* self = NULL;
    trace();

    NODE_API_CALL(
        napi_get_cb_info(env, info, &argc, NULL, &jsthis, NULL),
        "Could not get call info"
    );

    NODE_API_CALL(
        napi_unwrap(env, jsthis, reinterpret_cast<void**>(&self)),
        "Could not unwrap native instance"
    );

    NODE_API_CALL(
        napi_create_uint32(env, self->whatever, &result),
        "Could not create return value"
    );

    return result;
}

napi_value nci_native_device_open_file(napi_env env, napi_callback_info info) {
    napi_status status;
    size_t argcount = 1;
    napi_value args[1];
    napi_value jsthis;
    char buffer[255];
    size_t buffer_len_result;
    nci_native_device_struct* self = NULL;

    trace();

    NODE_API_CALL(
        napi_get_cb_info(env, info, &argcount, args, &jsthis, NULL),
        "Could not get call info"
    );

    if (argcount != 1) {
        napi_throw_error(env, NULL, "Missing argument: some_file");
    }

    NODE_API_CALL(
        napi_get_value_string_utf8(env, args[0], buffer, sizeof(buffer), &buffer_len_result),
        "Could not get native buffer"
    );

    NODE_API_CALL(
        napi_unwrap(env, jsthis, reinterpret_cast<void**>(&self)),
        "Could not unwrap native instance"
    );

    if (NULL != self->some_file){
        fclose(self->some_file);
        printf(">> Closed some_file\n");
        self->some_file = NULL;
    }

    self->some_file = fopen(buffer, "r");
    printf(">> Opened file '%s'\n", buffer);

    return NULL != self->some_file ? jsTRUE : jsFALSE;
}

napi_value nci_constructor(napi_env env, napi_callback_info info) {
    napi_status status;
    napi_value target;
    napi_value jsthis;
    trace();

    NODE_API_CALL(
        napi_get_new_target(env, info, &target),
        "Could not get new target"
    );

    bool is_constructor = target != NULL;
    if (is_constructor) {
        size_t argcount = 1;
        napi_value args[1];
        int32_t value = 0;
        napi_value js_nci_native_device_get_whatever;
        napi_value js_nci_native_device_open_file;

        NODE_API_CALL(
            napi_get_cb_info(env, info, &argcount, args, &jsthis, NULL),
            "Could not get call info"
        );

        if (argcount != 1) {
            napi_throw_error(env, NULL, "Missing argument: whatever");
        }

        NODE_API_CALL(
            napi_get_value_int32(env, args[0], &value),
            "Could not get int32_t value from first argument"
        );

        nci_native_device_struct* obj = new_nci_native_device(value);

        NODE_API_CALL(
            napi_wrap(env, jsthis, reinterpret_cast<void*>(obj), free_nci_native_device, NULL, NULL),
            "Could not wrap native device"
        );

        NODE_API_CALL(
            napi_create_function(
                env,
                FCT_NAME_get_whatever,
                FCT_NAME_LENGTH_get_whatever,
                nci_native_device_get_whatever,
                NULL,
                &js_nci_native_device_get_whatever
            ),
            "Could not wrap `nci_native_device_get_whatever`"
        );

        NODE_API_CALL(
            napi_set_named_property(env, jsthis, FCT_NAME_get_whatever, js_nci_native_device_get_whatever),
            "Could not add `get_whatever` to js instance"
        );

        NODE_API_CALL(
            napi_create_function(
                env,
                FCT_NAME_open_file,
                FCT_NAME_LENGTH_open_file,
                nci_native_device_open_file,
                NULL,
                &js_nci_native_device_open_file
            ),
            "Could not wrap `nci_native_device_open_file`"
        );

        NODE_API_CALL(
            napi_set_named_property(env, jsthis, FCT_NAME_open_file, js_nci_native_device_open_file),
            "Could not add `open_file` to js instance"
        );
    } else {
        napi_throw_error(env, NULL, "Unsupported, use `new ClassName()`");
    }

    return jsthis;
}

napi_value Init(napi_env env, napi_value exports) {
    napi_status status;
    napi_value nci_class;
    napi_value js_nci_class_name;
    trace();

    NODE_API_CALL(
        napi_create_string_utf8(env, NCI_CLASS_NAME, NCI_CLASS_NAME_LENGTH, &js_nci_class_name),
        "Could not wrap class name"
    );

    NODE_API_CALL(
        napi_define_class(env, NCI_CLASS_NAME, NCI_CLASS_NAME_LENGTH, nci_constructor, NULL, 0, NULL, &nci_class),
        "Could not define class"
    );

    NODE_API_CALL(
        napi_set_named_property(env, exports, NCI_CLASS_NAME, nci_class),
        "Could not export class"
    );

    // Store a few globals for later
    NODE_API_CALL(
        napi_get_boolean(env, true, &jsTRUE),
        "Could not get global *true*"
    );
    NODE_API_CALL(
        napi_get_boolean(env, false, &jsFALSE),
        "Could not get global *false*"
    );

    return exports;
}


NAPI_MODULE(NODE_GYP_MODULE_NAME, Init)
