#Makefile example
# @author Damho Lee

SHELL=cmd.exe

CC=gcc
CXX=g++
CFLAGS=-g


all: example
	$(CC) $(CFLAGS) -o example $<.o
	
clean:
	-erase example.o
	-erase example.exe

example: example.c
	$(CC) $(CFLAGS) -c -o $@.o $<