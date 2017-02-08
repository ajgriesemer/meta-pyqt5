SUMMARY = "A PyQt5 development image"
HOMEPAGE = "http://www.github.com/ajgriesemer"
LICENSE = "MIT"

require qt5-image.bb

PYQT5_PKGS = " \
"

IMAGE_INSTALL += " \
    ${PYQT5_PKGS} \
"

export IMAGE_BASENAME = "pyqt5-image"
