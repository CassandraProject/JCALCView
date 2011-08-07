#!/bin/sh

rm -r CALCViewApp
mkdir CALCViewApp

javac Source/*java
mv Source/*class CALCViewApp
cp -r Resources CALCViewApp
cp Localizations/*properties CALCViewApp

cd CALCViewApp
jar cvfm ../CALCView.jar ../MANIFEST.MF *
cd ..
