#!/bin/sh

rm -r CALCViewActApp
mkdir CALCViewActApp

javac Source/*java
mv Source/*class CALCViewActApp
cp -r Resources CALCViewActApp
cp Localizations/*properties CALCViewActApp
cp MANIFESTACT.MF CALCViewActApp/MANIFEST.MF

cd CALCViewActApp
jar cvfm ../CALCViewAct.jar MANIFEST.MF *
cd ..
