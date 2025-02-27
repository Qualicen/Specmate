pyinstaller --noconfirm \
	    --add-data bin:bin \
    	    --add-data venv/lib/$(ls venv/lib)/site-packages/torch:torch \
    	    --add-data venv/lib/$(ls venv/lib)/site-packages/torch-1.13.0.dist-info:torch-1.13.0.dist-info \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/tokenizers:tokenizers \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/tokenizers-0.13.2.dist-info:tokenizers-0.13.2.dist-info \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/numpy:numpy \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/numpy-1.23.4.dist-info:numpy-1.23.4.dist-info \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/filelock:filelock \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/filelock-3.8.0.dist-info:filelock-3.8.0.dist-info \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/packaging:packaging \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/packaging-21.3.dist-info:packaging-21.3.dist-info \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/requests:requests \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/requests-2.28.1.dist-info:requests-2.28.1.dist-info \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/regex:regex \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/regex-2022.10.31.dist-info:regex-2022.10.31.dist-info \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/tqdm:tqdm \
	    --add-data venv/lib/$(ls venv/lib)/site-packages/tqdm-4.64.1.dist-info:tqdm-4.64.1.dist-info \
	    --paths venv/lib/$(ls venv/lib)/site-packages \
	    service.py
