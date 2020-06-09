enum DIRECTION {
	DOWN,
	UP,
	STOP
}

enum OCCUPIE_STATUS {
	FULL,
	HAVE_OCCUPIE
}

enum OPEN_CLOSE_STATUS {
	CLOSE,
	OPEN
}

enum STATUS {
	OPEN1, // Trạng thái mở của để đón người
	CLOSE1, // Trạng thái đóng cửa để đón người
	OPEN2, // Mở cửa để trả người
	CLOSE2, // Đóng cửa để trả người
	UP1, // Di chuyển lên để đón người
	DOWN1, // Di chuyển xuống để đón người
	UP2, // Di chuyển lên để trả người
	DOWN2 // Di chuyên xuống để trả người
}
