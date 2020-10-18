/***
 * Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
 ***/

#include "timer.hpp"

#include <ruby.h>

#include <stdio.h>
#include <iostream>

#define trace() \
    std::cout << "> " << __func__ << std::endl;

typedef VALUE (*RBMETHOD)(...);

class NCIppNativeDevice {
public:
    NCIppNativeDevice() {
        this->whatever = 0;
        this->some_file = NULL;
    }

    ~NCIppNativeDevice() {
        this->close_file();
    }

    void close_file() {
        if (NULL != this->some_file) {
            fclose(this->some_file);
            std::cout << ">> Closed some_file" << std::endl;
            this->some_file = NULL;
        }
    }

    bool open_file(const char* file_path) {
        this->close_file();

        this->some_file = fopen(file_path, "r");
        std::cout << ">> Opened file " << file_path << std::endl;

        return NULL != this->some_file;
    }

    void SetWhatever(uint32_t value) {
        if (0 == value){
            rb_raise(rb_eArgError, "invalid 'whatever' value (0)");
        }

        this->whatever = value;
    }

    uint32_t GetWhatever() {
        return this->whatever;
    }

    void DoSomethingSlow() {
        TimeIt timer("DoSomethingSlow");
        sleep(5);
    }

private:
    uint32_t whatever;
    FILE* some_file;
};

extern "C" {
    void
    ncipp_native_device_free(void* rb_data) {
        NCIppNativeDevice *data;
        trace();

        data = (NCIppNativeDevice *)rb_data;
        delete data;
    }

    static VALUE
    ncipp_native_device_alloc(VALUE klass) {
        NCIppNativeDevice *cself;
        trace();

        cself = new NCIppNativeDevice();
        return Data_Wrap_Struct(klass, NULL, ncipp_native_device_free, cself);
    }

    static VALUE
    ncipp_native_device_init(VALUE self, VALUE rb_whatever) {
        NCIppNativeDevice *cself;
        uint32_t whatever = NUM2SIZET(rb_whatever);
        trace();

        Data_Get_Struct(self, NCIppNativeDevice, cself);
        cself->SetWhatever(whatever);

        return self;
    }

    static VALUE
    ncipp_native_device_open_file(VALUE self, VALUE rb_file_path) {
        NCIppNativeDevice *cself;
        const char* file_path = StringValueCStr(rb_file_path);
        trace();

        Data_Get_Struct(self, NCIppNativeDevice, cself);
        return cself->open_file(file_path) ? Qtrue : Qfalse;
    }

    static VALUE
    ncipp_native_device_get_whatever(VALUE self) {
        NCIppNativeDevice *cself;
        trace();

        Data_Get_Struct(self, NCIppNativeDevice, cself);
        return SIZET2NUM(cself->GetWhatever());
    }

    static VALUE
    ncipp_native_device_do_something_slow(VALUE self) {
        NCIppNativeDevice *cself;
        trace();

        Data_Get_Struct(self, NCIppNativeDevice, cself);
        cself->DoSomethingSlow();
        return Qnil;
    }

    void
    Init_ncipp_native(void) {
        VALUE mNCIpp;
        VALUE cNCIppNativeDevice;
        trace();

        mNCIpp = rb_const_get(rb_cObject, rb_intern("NCIpp"));
        cNCIppNativeDevice = rb_define_class_under(mNCIpp, "NCIppNativeDevice", rb_cObject);

        // Alloc and init
        rb_define_alloc_func(cNCIppNativeDevice, ncipp_native_device_alloc);
        rb_define_method(cNCIppNativeDevice, "initialize", (RBMETHOD)ncipp_native_device_init, 1);

        // Other Methods
        rb_define_method(cNCIppNativeDevice, "open_file", (RBMETHOD)ncipp_native_device_open_file, 1);
        rb_define_method(cNCIppNativeDevice, "get_whatever", (RBMETHOD)ncipp_native_device_get_whatever, 0);
        rb_define_method(cNCIppNativeDevice, "do_something_slow", (RBMETHOD)ncipp_native_device_do_something_slow, 0);
    }
}
