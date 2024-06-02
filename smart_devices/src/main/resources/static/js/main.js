document.getElementById('imageInput').addEventListener('change', function() {
            const selectedImage = document.getElementById('selectedImage');
            const files = this.files;
            if (files && files.length > 0) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    selectedImage.src = e.target.result;
                };
                reader.readAsDataURL(files[0]); // Chỉ đọc và hiển thị ảnh đầu tiên
            }
        });

