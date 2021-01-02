/***
 * Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/
#include <ruby.h>

#include <stdio.h>

#define trace() \
    printf("> %s\n", __func__)

struct nci_native_device {
    uint32_t whatever;
    FILE* some_file;
};

void
nci_native_device_free(void* rb_data) {
    struct nci_native_device *data;
    trace();

    data = (struct nci_native_device *)rb_data;

    if (NULL != data->some_file) {
        fclose(data->some_file);
        printf(">> Closed some_file\n");
        data->some_file = NULL;
    }
}

static VALUE
nci_native_device_alloc(VALUE klass) {
    VALUE obj;
    struct nci_native_device *cself;
    trace();

    obj = Data_Make_Struct(klass, struct nci_native_device, NULL, nci_native_device_free, cself);
    cself->whatever = 0;
    cself->some_file = NULL;

    return obj;
}

static VALUE
nci_native_device_init(VALUE self, VALUE rb_whatever) {
    struct nci_native_device *cself;
    uint32_t whatever = NUM2SIZET(rb_whatever);
    trace();

    if (0 == whatever){
        rb_raise(rb_eArgError, "invalid 'whatever' value (0)");
    }

    Data_Get_Struct(self, struct nci_native_device, cself);
    cself->whatever = whatever;

    return self;
}

static VALUE
nci_native_device_open_file(VALUE self, VALUE rb_file_path) {
    struct nci_native_device *cself;
    const char* file_path = StringValueCStr(rb_file_path);
    trace();

    Data_Get_Struct(self, struct nci_native_device, cself);
    if (NULL != cself->some_file){
        fclose(cself->some_file);
        printf(">> Closed some_file\n");
        cself->some_file = NULL;
    }

    cself->some_file = fopen(file_path, "r");
    printf(">> Opened file '%s'\n", file_path);

    return NULL != cself->some_file ? Qtrue : Qfalse;
}

static VALUE
nci_native_device_get_whatever(VALUE self) {
    struct nci_native_device *cself;
    trace();

    Data_Get_Struct(self, struct nci_native_device, cself);
    return SIZET2NUM(cself->whatever);
}

void
Init_nci_native(void) {
    VALUE mNCI;
    VALUE cNCINativeDevice;
    trace();

    mNCI = rb_const_get(rb_cObject, rb_intern("NCI"));
    cNCINativeDevice = rb_define_class_under(mNCI, "NCINativeDevice", rb_cObject);

    // Alloc and init
    rb_define_alloc_func(cNCINativeDevice, nci_native_device_alloc);
    rb_define_method(cNCINativeDevice, "initialize", nci_native_device_init, 1);

    // Other Methods
    rb_define_method(cNCINativeDevice, "open_file", nci_native_device_open_file, 1);
    rb_define_method(cNCINativeDevice, "get_whatever", nci_native_device_get_whatever, 0);
}
