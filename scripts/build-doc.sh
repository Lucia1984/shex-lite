#!/usr/bin/env bash
set -e

for index in */index.bs; do
  bikeshed spec $index;
done
